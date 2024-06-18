package com.Java020.MonieFlex.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "BVN is mandatory")
    @NotBlank(message = "BVN is mandatory")
    private String BVN;

    @NotEmpty(message = "First name is mandatory")
    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 125, message = "First name must be at least 2 characters long")
    private String firstName;

    private String middleName;

    @NotEmpty(message = "Last name is mandatory")
    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 125, message = "Lastname must be at least 2 characters long")
    @Email(message = "Must have correct email format")
    private String lastName;
    private String phoneNumber;

    @NotEmpty(message = "Address is mandatory")
    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should be 6 characters minimum")
    private String password;

    @NotEmpty(message = "Pin is mandatory")
    @NotBlank(message = "Pin is mandatory")
    @Size(min = 4, max = 4,message = "Pin should be 4 characters long")
    private String transactionPin;
}
