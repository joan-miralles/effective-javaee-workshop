package es.joanmiralles.doit.business.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CrossCheckConstraintValidator implements ConstraintValidator<CrossCheck, ValidEntity> {
    @Override
    public void initialize(CrossCheck priorityConstraint) {
    }

    @Override
    public boolean isValid(ValidEntity validEntity, ConstraintValidatorContext constraintValidatorContext) {
        return validEntity.isValid();
    }
}
