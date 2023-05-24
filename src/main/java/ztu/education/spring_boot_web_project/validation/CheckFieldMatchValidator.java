package ztu.education.spring_boot_web_project.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class CheckFieldMatchValidator implements ConstraintValidator<CheckFieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(CheckFieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.field();
        this.secondFieldName = constraintAnnotation.fieldMatch();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
        boolean isValid = false;

        try {
            Field fieldObject = object.getClass().getDeclaredField(this.firstFieldName);
            Field fieldMatchObject = object.getClass().getDeclaredField(this.secondFieldName);

            fieldObject.setAccessible(true);
            fieldMatchObject.setAccessible(true);

            Object fieldValue = fieldObject.get(object);
            Object fieldMatchValue = fieldMatchObject.get(object);

            isValid = fieldValue != null && fieldValue.equals(fieldMatchValue);
        } catch (Exception ex) {
            isValid = false;
        }

        if (!isValid) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext
                    .buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode(this.secondFieldName)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
