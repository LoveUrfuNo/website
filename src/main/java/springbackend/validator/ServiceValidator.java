/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.validator;

import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import springbackend.model.Service;

/**
 * Validator for {@link springbackend.model.Service} class,
 * implements {@link Validator} interface.
 */
@Component
public class ServiceValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Service.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Service service = (Service) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nameOfService", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceCost", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typeOfService", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currency", "Required");
    }
}