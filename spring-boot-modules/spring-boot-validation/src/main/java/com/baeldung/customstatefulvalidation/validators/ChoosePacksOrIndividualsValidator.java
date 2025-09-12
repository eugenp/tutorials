package com.baeldung.customstatefulvalidation.validators;

import com.baeldung.customstatefulvalidation.model.PurchaseOrderItem;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChoosePacksOrIndividualsValidator implements ConstraintValidator<ChoosePacksOrIndividuals, PurchaseOrderItem> {
    @Override
    public boolean isValid(PurchaseOrderItem value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        boolean isValid = true;

        if ((value.getNumberOfPacks() == 0) == (value.getNumberOfIndividuals() == 0)) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            // either both are zero, or both are turned on
            if (value.getNumberOfPacks() == 0) {
                context.buildConstraintViolationWithTemplate("must choose a quantity when no packs")
                        .addPropertyNode("numberOfIndividuals")
                        .addConstraintViolation();
                context.buildConstraintViolationWithTemplate("must choose a quantity when no individuals")
                        .addPropertyNode("numberOfPacks")
                        .addConstraintViolation();
            } else {
                context.buildConstraintViolationWithTemplate("cannot be combined with number of packs")
                        .addPropertyNode("numberOfIndividuals")
                        .addConstraintViolation();
                context.buildConstraintViolationWithTemplate("cannot be combined with number of individuals")
                        .addPropertyNode("numberOfPacks")
                        .addConstraintViolation();
            }
        }

        if (value.getNumberOfPacks() > 0 && value.getItemsPerPack() == 0) {
            isValid = false;

            context.buildConstraintViolationWithTemplate("cannot be 0 when using packs")
                    .addPropertyNode("itemsPerPack")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
