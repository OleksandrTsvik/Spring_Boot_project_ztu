package ztu.education.spring_boot_web_project.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckFieldMatchValidator.class)
public @interface CheckFieldMatch {
    String field();

    String fieldMatch();

    String message() default "Fields do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /* Для групування декількох анотацій над одним класом
    @CheckFieldMatch.List({
        @CheckFieldMatch(field = "password", fieldMatch = "confirmPassword", message = "Паролі не співпадають"),
        @CheckFieldMatch(field = "password", fieldMatch = "confirmPassword", message = "Паролі не співпадають")
    })
    * */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        CheckFieldMatch[] value();
    }
}
