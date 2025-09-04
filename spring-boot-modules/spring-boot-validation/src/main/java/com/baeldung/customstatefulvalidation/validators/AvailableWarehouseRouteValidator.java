package com.baeldung.customstatefulvalidation.validators;

import com.baeldung.customstatefulvalidation.model.PurchaseOrderItem;
import com.baeldung.customstatefulvalidation.repository.WarehouseRouteRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class AvailableWarehouseRouteValidator implements ConstraintValidator<AvailableWarehouseRoute, PurchaseOrderItem> {
    @Autowired
    private WarehouseRouteRepository warehouseRouteRepository;

    @Override
    public boolean isValid(PurchaseOrderItem value, ConstraintValidatorContext context) {
        return warehouseRouteRepository.isWarehouseRouteAvailable(value.getSourceWarehouse(), value.getDestinationCountry());
    }
}
