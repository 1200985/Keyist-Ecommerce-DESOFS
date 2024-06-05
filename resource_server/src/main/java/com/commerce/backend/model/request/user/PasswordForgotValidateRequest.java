package com.commerce.backend.model.request.user;

import com.commerce.backend.validator.PasswordIsStrong;
import com.commerce.backend.validator.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class PasswordForgotValidateRequest {

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 8, max = 64)
    @PasswordIsStrong
    private String newPassword;

    @NotBlank
    @Size(min = 8, max = 64)
    private String newPasswordConfirm;
}
