/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import springbackend.model.User;
import springbackend.service.UserService;

/**
 * Validator for {@link springbackend.model.User} class,
 * implements {@link Validator} interface.
 */
@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if (!errors.getAllErrors().contains(errors.getFieldError("username"))) {
            if (user.getUsername().length() < 8 || user.getUsername().length() > 64) {
                errors.rejectValue("username", "Size.userForm.username");
            }

            if (this.userService.findByUsername(user.getUsername()) != null) {
                errors.rejectValue("username", "Duplicate.userForm.username");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "Required");
        if (!errors.getAllErrors().contains(errors.getFieldError("login"))) {
            if (user.getLogin().length() < 8 || user.getLogin().length() > 32) {
                errors.rejectValue("login", "Size.userForm.login");
            }

            if (this.userService.findByLogin(user.getLogin()) != null) {
                errors.rejectValue("login", "Duplicate.userForm.login");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (!errors.getAllErrors().contains(errors.getFieldError("password"))) {
            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
                errors.rejectValue("password", "Size.userForm.password");
            }

            if (!user.getConfirmPassword().equals(user.getPassword())) {
                errors.rejectValue("confirmPassword", "Different.userForm.password");
            }
        }
    }
}
