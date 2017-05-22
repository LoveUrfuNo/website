package springbackend.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import springbackend.model.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Validator for {@link springbackend.model.User} class,
 * implements {@link Validator} interface.
 */
@Component
public class UserOptionsValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Required");
        if (!errors.getAllErrors().contains(errors.getFieldError("firstName"))) {
            if (user.getFirstName().length() < 2 || user.getFirstName().length() > 32) {
                errors.rejectValue("firstName", "Size.userForm.firstname");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "Required");
        if (!errors.getAllErrors().contains(errors.getFieldError("dateOfBirth"))) {
            if (user.getDateOfBirth().length() != 10) {
                errors.rejectValue("dateOfBirth", "Size.userForm.dateOfBirth");
            } else {
                Calendar calendar = Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
                calendar.setTime(new Date());

                Integer year = Integer.parseInt(user.getDateOfBirth().substring(0, 4));
                if (year > calendar.get(Calendar.YEAR) || year < 1899) {
                    errors.rejectValue("dateOfBirth", "Size.userForm.year.dateOfBirth");
                }

                Integer month = Integer.parseInt(user.getDateOfBirth().substring(5, 7));
                if (month > 12 || month < 1) {
                    errors.rejectValue("dateOfBirth", "Size.userForm.month.dateOfBirth");
                }

                Integer day = Integer.parseInt(user.getDateOfBirth().substring(8, 10));
                if (day > 31 || day < 1) {
                    errors.rejectValue("dateOfBirth", "Size.userForm.day.dateOfBirth");
                }
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "Required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "Required");
    }
}
