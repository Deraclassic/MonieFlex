package com.Java020.MonieFlex.payload.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @Size(min = 11, max = 11, message = "BVN must be 11 characters long")
    @NotBlank(message = "BVN must not be empty")
    @Digits(fraction = 0, integer = 11, message = "BVN must be 11 characters long")
    private String BVN;

    @Size(min = 2, max = 35, message = "First name must be at least 2 characters")
    @NotBlank(message = "First name must not be empty")
    private String firstName;

    private String middleName;

    @Size(min = 2, max = 35, message = "Last name must be at least 2 characters")
    @NotBlank(message = "Last name must not be empty")
    private String lastName;

    @Size(min = 11, max = 15, message = "Phone number is too short or too long")
    @NotBlank(message = "Phone number must not be empty")
    @Digits(fraction = 0, integer = 11, message = "Phone number must be at least 10 characters long")
    private String phoneNumber;

    @Email(message = "Invalid email")
    @NotBlank(message = "email must not be empty")
    private String email;

    @Size(min = 10, max = 20, message = "Phone number must be at least 10 characters long")
    @NotBlank(message = "Phone number must not be empty")
    private String password;

//    @Size(min = 4, max = 4, message = "Phone number must be 4 characters long")
//    @NotBlank(message = "Pin must not be empty")
//    @Digits(fraction = 0, integer = 4, message = "Pin must be 4 characters long")
//    private String transactionPin;

    private String address;

}