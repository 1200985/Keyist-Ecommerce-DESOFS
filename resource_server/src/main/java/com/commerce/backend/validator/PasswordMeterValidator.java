package com.commerce.backend.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMeterValidator implements ConstraintValidator<PasswordIsStrong, String> {

    @Override
    public void initialize(PasswordIsStrong constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return isStrong(password);
    }

    private boolean isStrong(String password) {
        return password.length() >= 8 && hasMixedCase(password) && hasDigits(password) && hasSpecialCharacters(password);
    }

    private boolean hasMixedCase(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z]).+$");
    }

    private boolean hasDigits(String password) {
        return password.matches("^(?=.*\\d).+$");
    }

    private boolean hasSpecialCharacters(String password) {
        return password.matches("^(?=.*[@#$%^&+=!?]).+$");
    }
}
