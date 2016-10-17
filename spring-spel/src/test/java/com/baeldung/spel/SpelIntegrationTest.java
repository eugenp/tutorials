package com.baeldung.spel;

import com.baeldung.spring.spel.examples.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpelIntegrationTest {

    @Autowired
    private SpelArithmetic spelArithmetic = new SpelArithmetic();

    @Autowired
    private SpelCollections spelCollections = new SpelCollections();

    @Autowired
    private SpelConditional spelConditional = new SpelConditional();

    @Autowired
    private SpelLogical spelLogical = new SpelLogical();

    @Autowired
    private SpelRegex spelRegex = new SpelRegex();

    @Autowired
    private SpelRelational spelRelational = new SpelRelational();

    @Test
    public void testArithmetic() throws Exception {
        assertThat(spelArithmetic.getAdd(), equalTo(20.0));
        assertThat(spelArithmetic.getAddString(), equalTo("Some string plus other string"));
        assertThat(spelArithmetic.getSubtract(), equalTo(19.0));
        assertThat(spelArithmetic.getMultiply(), equalTo(20.0));
        assertThat(spelArithmetic.getDivide(), equalTo(18.0));
        assertThat(spelArithmetic.getDivideAlphabetic(), equalTo(18.0));
        assertThat(spelArithmetic.getModulo(), equalTo(7.0));
        assertThat(spelArithmetic.getModuloAlphabetic(), equalTo(7.0));
        assertThat(spelArithmetic.getPowerOf(), equalTo(512.0));
        assertThat(spelArithmetic.getBrackets(), equalTo(17.0));
    }

    @Test
    public void testCollections() throws Exception {
        assertThat(spelCollections.getDriver1Car().getModel(), equalTo("Model1"));
        assertThat(spelCollections.getDriver2Car().getModel(), equalTo("Model2"));
        assertThat(spelCollections.getFirstCarInPark().getModel(), equalTo("Model1"));
        assertThat(spelCollections.getNumberOfCarsInPark(), equalTo(2));
    }

    @Test
    public void testConditional() throws Exception {
        assertThat(spelConditional.getTernary(), equalTo("Something went wrong. There was false value"));
        assertThat(spelConditional.getTernary2(), equalTo("Some model"));
        assertThat(spelConditional.getElvis(), equalTo("Some model"));
    }

    @Test
    public void testLogical() throws Exception {
        assertThat(spelLogical.isAnd(), equalTo(true));
        assertThat(spelLogical.isAndAlphabetic(), equalTo(true));
        assertThat(spelLogical.isOr(), equalTo(true));
        assertThat(spelLogical.isOrAlphabetic(), equalTo(true));
        assertThat(spelLogical.isNot(), equalTo(false));
        assertThat(spelLogical.isNotAlphabetic(), equalTo(false));
    }

    @Test
    public void testRegex() throws Exception {
        assertThat(spelRegex.isValidNumericStringResult(), equalTo(true));
        assertThat(spelRegex.isInvalidNumericStringResult(), equalTo(false));
        assertThat(spelRegex.isValidAlphabeticStringResult(), equalTo(true));
        assertThat(spelRegex.isInvalidAlphabeticStringResult(), equalTo(false));
        assertThat(spelRegex.isValidFormatOfHorsePower(), equalTo(true));
    }

    @Test
    public void testRelational() throws Exception {
        assertThat(spelRelational.isEqual(), equalTo(true));
        assertThat(spelRelational.isEqualAlphabetic(), equalTo(true));
        assertThat(spelRelational.isNotEqual(), equalTo(false));
        assertThat(spelRelational.isNotEqualAlphabetic(), equalTo(false));
        assertThat(spelRelational.isLessThan(), equalTo(false));
        assertThat(spelRelational.isLessThanAlphabetic(), equalTo(false));
        assertThat(spelRelational.isLessThanOrEqual(), equalTo(true));
        assertThat(spelRelational.isLessThanOrEqualAlphabetic(), equalTo(true));
        assertThat(spelRelational.isGreaterThan(), equalTo(false));
        assertThat(spelRelational.isGreaterThanAlphabetic(), equalTo(false));
        assertThat(spelRelational.isGreaterThanOrEqual(), equalTo(true));
        assertThat(spelRelational.isGreaterThanOrEqualAlphabetic(), equalTo(true));
        assertThat(spelRelational.isAnd(), equalTo(true));
        assertThat(spelRelational.isAndAlphabetic(), equalTo(true));
        assertThat(spelRelational.isOr(), equalTo(true));
        assertThat(spelRelational.isOrAlphabetic(), equalTo(true));
        assertThat(spelRelational.isNot(), equalTo(false));
        assertThat(spelRelational.isNotAlphabetic(), equalTo(false));
    }
}
