package com.commerce.backend.model.request.user;

import com.commerce.backend.validator.CustomEmail;
import com.commerce.backend.validator.PasswordIsStrong;
import com.commerce.backend.validator.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class RegisterUserRequest {

    @NotBlank
    @Size(min = 3, max = 52)
    @CustomEmail
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    @PasswordIsStrong
    private String password;

    @NotBlank
    @Size(min = 8, max = 64)
    private String passwordRepeat;
}
