package com.baeldung.attribute.overwrite;

import com.baeldung.Application;
import com.baeldung.attribute.overwrite.entity.Address;
import com.baeldung.attribute.overwrite.entity.Brand;
import com.baeldung.attribute.overwrite.entity.Car;
import com.baeldung.attribute.overwrite.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public class AttributeOverwriteIntegrationTest {

    private static final LocalDate FORD_FOUNDATION_DATE = LocalDate.parse("1903-06-16");
    @Autowired
    CarRepository carRepository;

    @Test
    @Transactional
    public void whenInsertingCar_thenEmbeddedAndMappedFieldsArePopulated() {

        Car fordMustang = createMustang();

        carRepository.save(fordMustang);
        Car actualCar = carRepository.getOne(fordMustang.getId());

        Assertions.assertThat(actualCar).isEqualTo(fordMustang);
    }

    @NotNull
    private Car createMustang() {
        Address address = new Address();
        address.setName("Ford United States");
        address.setCity("Dearborn");

        Brand ford = new Brand();
        ford.setName("Ford");
        ford.setFoundationDate(FORD_FOUNDATION_DATE);

        Car fordMustang = new Car();
        fordMustang.setIdentifier("WP1AB29P88LA47599");
        fordMustang.setModel("Ford");
        fordMustang.setName("My car");
        fordMustang.setBrand(ford);
        return fordMustang;
    }
}
