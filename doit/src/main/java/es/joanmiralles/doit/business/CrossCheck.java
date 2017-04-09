package es.joanmiralles.doit.business;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CrossCheckConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CrossCheck {
    String message() default "Cross check failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
