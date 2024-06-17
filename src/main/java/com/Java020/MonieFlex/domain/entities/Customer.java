package com.Java020.MonieFlex.domain.entities;

import com.Java020.MonieFlex.domain.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "customer_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Customer extends BaseClass implements UserDetails, Principal {
    private String BVN;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String address;
    @Column(unique = true)
    private String email;
    private String password;
    private String transactionPin;
    private boolean accountLocked;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> accounts;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Beneficiary> beneficiaries;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Issues> issues;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    private String fullName(){
        return firstName + " " + middleName + " " + lastName;
    }

    @Override
    public String getName() {
        return email;
    }
}
