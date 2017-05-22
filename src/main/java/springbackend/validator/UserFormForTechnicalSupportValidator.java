package springbackend.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import springbackend.model.UserFormForTechnicalSupport;

/**
 * Validator for {@link springbackend.model.User} class,
 * implements {@link Validator} interface.
 */
@Component
public class UserFormForTechnicalSupportValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserFormForTechnicalSupport.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserFormForTechnicalSupport userForSupport = (UserFormForTechnicalSupport) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if (!errors.getAllErrors().contains(errors.getFieldError("name"))) {
            if (userForSupport.getName().length() <= 1) {
                errors.rejectValue("name", "Size.userFormForTechnicalSupport.name");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "Required");
        if (!errors.getAllErrors().contains(errors.getFieldError("subject"))) {
            if (userForSupport.getSubject().length() <= 3) {
                errors.rejectValue("subject", "Size.userFormForTechnicalSupport.subject");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Required");
        if (!errors.getAllErrors().contains(errors.getFieldError("description"))) {
            if (userForSupport.getDescription().length() <= 20) {
                errors.rejectValue("description", "Size.userFormForTechnicalSupport.description");
            }
        }
    }
}
