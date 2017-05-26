/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import springbackend.model.SearchRequest;

/**
 * Validator for {@link springbackend.model.SearchRequest} class,
 * implements {@link Validator} interface.
 */
@Component
public class SearchValidator implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return SearchRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchRequest searchRequest = (SearchRequest) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"searchLine", "Required");
        if (!errors.getAllErrors().contains(errors.getFieldError("searchLine"))) {
            if (searchRequest.getSearchLine().length() >= 150 || searchRequest.getSearchLine().length() <= 1) {
                errors.rejectValue("searchLine", "Size.searchLine.size");
            }
        }
    }
}
