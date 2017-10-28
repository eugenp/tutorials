package org.baeldung.converter;

import com.baeldung.toggle.Employee;
import org.baeldung.Application;
import org.baeldung.domain.Modes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CustomConverterTest {

    @Autowired
    ConversionService conversionService;

    @Test
    public void whenConvertStringToIntegerUsingDefaultConverter_thenSuccess() {
        System.out.println(conversionService.convert("25", Integer.class));
    }

    @Test
    public void whenConvertStringToEmployee_thenSuccess() {
        System.out.println(conversionService.convert("1,50000.00", Employee.class));
    }

    @Test
    public void whenConvertStringToEnum_thenSuccess() {
        System.out.println(conversionService.convert("ALPHA", Modes.class));
    }

    @Test
    public void whenConvertingToBigDecimalUsingGenericConverter_thenSuccess() {
        System.out.println(conversionService.convert(Integer.valueOf(11), BigDecimal.class));
        System.out.println(conversionService.convert(Double.valueOf(25.23), BigDecimal.class));
        System.out.println(conversionService.convert("2.32", BigDecimal.class));
    }
}
