package baeldung;

import javax.enterprise.inject.Produces;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * Created by adam.
 */
public class ValidatorProducer {

    @Produces
    public Validator createValidator() {
        return Validation
          .byDefaultProvider()
          .configure()
          .buildValidatorFactory()
          .getValidator();
    }

}
