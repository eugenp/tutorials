package baeldung;

import javax.enterprise.inject.Produces;
import javax.validation.Validation;
import javax.validation.Validator;

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
