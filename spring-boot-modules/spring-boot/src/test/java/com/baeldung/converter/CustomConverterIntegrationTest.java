package com.baeldung.converter;

import com.baeldung.toggle.Employee;

import com.baeldung.boot.Application;
import com.baeldung.boot.domain.Modes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CustomConverterIntegrationTest {

    @Autowired
    ConversionService conversionService;

    @Test
    public void whenConvertStringToIntegerUsingDefaultConverter_thenSuccess() {
        assertThat(conversionService.convert("25", Integer.class)).isEqualTo(25);
    }

    @Test
    public void whenConvertStringToEmployee_thenSuccess() {
        Employee employee = conversionService.convert("1,50000.00", Employee.class);
        Employee actualEmployee = new Employee(1, 50000.00);
        assertThat(conversionService.convert("1,50000.00", Employee.class)).isEqualToComparingFieldByField(actualEmployee);
    }

    @Test
    public void whenConvertStringToEnum_thenSuccess() {
        assertThat(conversionService.convert("ALPHA", Modes.class)).isEqualTo(Modes.ALPHA);
    }

    @Test
    public void whenConvertingToBigDecimalUsingGenericConverter_thenSuccess() {
        assertThat(conversionService.convert(Integer.valueOf(11), BigDecimal.class)).isEqualTo(BigDecimal.valueOf(11.00).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        assertThat(conversionService.convert(Double.valueOf(25.23), BigDecimal.class)).isEqualByComparingTo(BigDecimal.valueOf(Double.valueOf(25.23)));
        assertThat(conversionService.convert("2.32", BigDecimal.class)).isEqualTo(BigDecimal.valueOf(2.32));
    }
}