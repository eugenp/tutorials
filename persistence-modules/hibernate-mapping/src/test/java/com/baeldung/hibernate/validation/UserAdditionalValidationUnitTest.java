package com.baeldung.hibernate.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Currency;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.LuhnCheck;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import org.javamoney.moneta.Money;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserAdditionalValidationUnitTest {

    private static Validator validator;
    private Set<ConstraintViolation<AdditionalValidations>> constraintViolations;

    @BeforeClass
    public static void before() {
        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        validator = config.getValidator();
    }

    @Test
    public void whenValidationWithCCNAndNullCCN_thenNoConstraintViolation() {
    	AdditionalValidations validations = new AdditionalValidations();
        constraintViolations = validator.validateProperty(validations, "creditCardNumber");
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void whenValidationWithCCNAndValidCCN_thenNoConstraintViolation() {
        AdditionalValidations validations = new AdditionalValidations();
        validations.setCreditCardNumber("79927398713");
        constraintViolations = validator.validateProperty(validations, "creditCardNumber");
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void whenValidationWithCCNAndInvalidCCN_thenConstraintViolation() {
    	AdditionalValidations validations = new AdditionalValidations();
        validations.setCreditCardNumber("79927398714");
        constraintViolations = validator.validateProperty(validations, "creditCardNumber");
        assertEquals(constraintViolations.size(), 2);
    }
    
    @Test
    public void whenValidationWithCCNAndValidCCNWithDashes_thenConstraintViolation() {
    	AdditionalValidations validations = new AdditionalValidations();
        validations.setCreditCardNumber("7992-7398-713");
        constraintViolations = validator.validateProperty(validations, "creditCardNumber");
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void whenValidationWithLenientCCNAndValidCCNWithDashes_thenNoConstraintViolation() {
    	AdditionalValidations validations = new AdditionalValidations();
        validations.setLenientCreditCardNumber("7992-7398-713");
        constraintViolations = validator.validateProperty(validations, "lenientCreditCardNumber");
        assertTrue(constraintViolations.isEmpty());
    }
 
    @Test
    public void whenMonetaryAmountWithRightCurrency_thenNoConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
        bean.setBalance(Money.of(new BigDecimal(100.0), Monetary.getCurrency("EUR")));
        constraintViolations = validator.validateProperty(bean, "balance");
        assertEquals(0, constraintViolations.size());
    }
    
    @Test
    public void whenMonetaryAmountWithWrongCurrency_thenConstraintViolation() {
    	AdditionalValidations validations = new AdditionalValidations();
        validations.setBalance(Money.of(new BigDecimal(100.0), Monetary.getCurrency("USD")));
        constraintViolations = validator.validateProperty(validations, "balance");
        assertEquals(1, constraintViolations.size());
    }
    
    @Test
    public void whenDurationShorterThanMin_thenConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	bean.setDuration(Duration.ofDays(1).plusHours(1));
        constraintViolations = validator.validateProperty(bean, "duration");
        assertEquals(1, constraintViolations.size());
    }
    
    @Test
    public void whenDurationLongerThanMax_thenConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	bean.setDuration(Duration.ofDays(2).plusHours(3));
        constraintViolations = validator.validateProperty(bean, "duration");
        assertEquals(1, constraintViolations.size());
    }
    
    @Test
    public void whenDurationBetweenMinAndMax_thenNoConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	bean.setDuration(Duration.ofDays(2));
        constraintViolations = validator.validateProperty(bean, "duration");
        assertEquals(0, constraintViolations.size());
    }
    
    @Test
    public void whenValueBelowRangeMin_thenConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	bean.setPercent(new BigDecimal("-1.4"));
        constraintViolations = validator.validateProperty(bean, "percent");
        assertEquals(1, constraintViolations.size());
    }
    
    @Test
    public void whenValueAboveRangeMax_thenConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	bean.setPercent(new BigDecimal("100.03"));
        constraintViolations = validator.validateProperty(bean, "percent");
        assertEquals(1, constraintViolations.size());
    }
    
    @Test
    public void whenValueInRange_thenNoConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	bean.setPercent(new BigDecimal("53.23"));
        constraintViolations = validator.validateProperty(bean, "percent");
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void whenLengthInRange_thenNoConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	bean.setSomeString("aaa");
        constraintViolations = validator.validateProperty(bean, "someString");
        assertEquals(0, constraintViolations.size());
    }
    
    @Test
    public void whenCodePointLengthNotInRange_thenConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	bean.setSomeString("aa\uD835\uDD0A");
        constraintViolations = validator.validateProperty(bean, "someString");
        assertEquals(1, constraintViolations.size());
    }
    
    @Test
    public void whenValidUrlWithWrongProtocol_thenConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	
    	bean.setUrl("https://www.google.com/");
        constraintViolations = validator.validateProperty(bean, "url");
        assertEquals(0, constraintViolations.size());
    	
    	bean.setUrl("http://www.google.com/");
        constraintViolations = validator.validateProperty(bean, "url");
        assertEquals(1, constraintViolations.size());
        
    	bean.setUrl("https://foo:bar");
        constraintViolations = validator.validateProperty(bean, "url");
        assertEquals(1, constraintViolations.size());
    }
    
    @Test
    public void whenScriptAssertFails_thenConstraintViolation() {
    	AdditionalValidations bean = new AdditionalValidations();
    	
    	constraintViolations = validator.validate(bean);
    	assertEquals(0, constraintViolations.size());
        
        bean.setValid(false);
    	
        constraintViolations = validator.validate(bean);
    	assertEquals(1, constraintViolations.size());
    	
    	constraintViolations = validator.validateProperty(bean, "valid");
    	assertEquals(0, constraintViolations.size());
    }

    @ScriptAssert(lang = "nashorn", script = "_this.valid")
    public class AdditionalValidations {
    	private boolean valid = true;
    	
        @CreditCardNumber
        @LuhnCheck(startIndex = 0, endIndex = Integer.MAX_VALUE, checkDigitIndex = -1)
        private String creditCardNumber;

        @CreditCardNumber(ignoreNonDigitCharacters = true)
        private String lenientCreditCardNumber;

        @Currency("EUR")
        private MonetaryAmount balance;
        
        @DurationMin(days = 1, hours = 2)
        @DurationMax(days = 2, hours = 1)
        private Duration duration;
        
        @Range(min = 0, max = 100)
        private BigDecimal percent;
        
        @Length(min = 1, max = 3)
        @CodePointLength(min = 1, max = 3)
        private String someString;
        
        @URL(protocol = "https")
        private String url;
        
        public String getCreditCardNumber() {
            return creditCardNumber;
        }

        public void setCreditCardNumber(String creditCardNumber) {
            this.creditCardNumber = creditCardNumber;
        }

        public String getLenientCreditCardNumber() {
            return lenientCreditCardNumber;
        }

        public void setLenientCreditCardNumber(String lenientCreditCardNumber) {
            this.lenientCreditCardNumber = lenientCreditCardNumber;
        }

        public MonetaryAmount getBalance() {
            return balance;
        }

        public void setBalance(MonetaryAmount balance) {
            this.balance = balance;
        }
        
        public Duration getDuration() {
			return duration;
		}
        
        public void setDuration(Duration duration) {
			this.duration = duration;
		}

		public BigDecimal getPercent() {
			return percent;
		}

		public void setPercent(BigDecimal percent) {
			this.percent = percent;
		}

		public String getSomeString() {
			return someString;
		}

		public void setSomeString(String someString) {
			this.someString = someString;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}
		
    }
}
