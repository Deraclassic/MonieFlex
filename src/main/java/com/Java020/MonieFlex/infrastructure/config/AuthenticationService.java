package com.Java020.MonieFlex.infrastructure.config;

import com.Java020.MonieFlex.domain.entities.ConfirmationToken;
import com.Java020.MonieFlex.domain.entities.Customer;
import com.Java020.MonieFlex.domain.enums.EmailTemplateName;
import com.Java020.MonieFlex.payload.request.AuthenticationRequest;
import com.Java020.MonieFlex.payload.request.RegistrationRequest;
import com.Java020.MonieFlex.payload.response.AuthenticationResponse;
import com.Java020.MonieFlex.repository.ConfirmationTokenRepository;
import com.Java020.MonieFlex.repository.CustomerRepository;
import com.Java020.MonieFlex.repository.RoleRepository;
import com.Java020.MonieFlex.service.impl.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final ConfirmationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${frontend-host}")
    private String activateUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                //TODO - better exception handling
                .orElseThrow(()-> new IllegalStateException("Role USER was not initialized"));
        var user = Customer.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .BVN(request.getBVN())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .transactionPin(passwordEncoder.encode(request.getTransactionPin()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        customerRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(Customer user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activateUrl,
                newToken,
                "Account Activation"
        );
    }

    @Transactional
    private String generateAndSaveActivationToken(Customer user) {
        //generate token
        String generatedToken = generateActivationCode(6);
        var token = ConfirmationToken.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .customer(user)
                .build();
        try {
            tokenRepository.save(token);
        } catch (Exception e) {
            // Handle the exception, e.g., log it
            e.printStackTrace();
            return null; // Return null or throw an exception based on your error handling strategy
        }


        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i<length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((Customer)auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        ConfirmationToken saveToken = tokenRepository.findByToken(token)
                //todo exception has to be defined
                .orElseThrow(()-> new RuntimeException("Invalid Token"));
        if (LocalDateTime.now().isAfter(saveToken.getExpiresAt())){
            sendValidationEmail(saveToken.getCustomer());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email address");
        }
        var user = customerRepository.findById(saveToken.getCustomer().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        customerRepository.save(user);
        saveToken.setConfirmedAt(LocalDateTime.now());
        tokenRepository.save(saveToken);
    }
}
