package com.baeldung.spel;

import com.baeldung.spring.spel.examples.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class SpelTest {

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
        Assert.assertThat(spelArithmetic.getAdd(), equalTo(20.0));
        Assert.assertThat(spelArithmetic.getAddString(), equalTo("Some string plus other string"));
        Assert.assertThat(spelArithmetic.getSubtract(), equalTo(19.0));
        Assert.assertThat(spelArithmetic.getMultiply(), equalTo(20.0));
        Assert.assertThat(spelArithmetic.getDivide(), equalTo(18.0));
        Assert.assertThat(spelArithmetic.getDivideAlphabetic(), equalTo(18.0));
        Assert.assertThat(spelArithmetic.getModulo(), equalTo(7.0));
        Assert.assertThat(spelArithmetic.getModuloAlphabetic(), equalTo(7.0));
        Assert.assertThat(spelArithmetic.getPowerOf(), equalTo(512.0));
        Assert.assertThat(spelArithmetic.getBrackets(), equalTo(17.0));
    }

    @Test
    public void testCollections() throws Exception {
        Assert.assertThat(spelCollections.getDriver1Car().getModel(), equalTo("Model1"));
        Assert.assertThat(spelCollections.getDriver2Car().getModel(), equalTo("Model2"));
        Assert.assertThat(spelCollections.getFirstCarInPark().getModel(), equalTo("Model1"));
        Assert.assertThat(spelCollections.getNumberOfCarsInPark(), equalTo(2));
    }

    @Test
    public void testConditional() throws Exception {
        Assert.assertThat(spelConditional.getTernary(), equalTo("Something went wrong. There was false value"));
        Assert.assertThat(spelConditional.getTernary2(), equalTo("Some model"));
        Assert.assertThat(spelConditional.getElvis(), equalTo("Some model"));
    }

    @Test
    public void testLogical() throws Exception {
        Assert.assertThat(spelLogical.isAnd(), equalTo(true));
        Assert.assertThat(spelLogical.isAndAlphabetic(), equalTo(true));
        Assert.assertThat(spelLogical.isOr(), equalTo(true));
        Assert.assertThat(spelLogical.isOrAlphabetic(), equalTo(true));
        Assert.assertThat(spelLogical.isNot(), equalTo(false));
        Assert.assertThat(spelLogical.isNotAlphabetic(), equalTo(false));
    }

    @Test
    public void testRegex() throws Exception {
        Assert.assertThat(spelRegex.isValidNumericStringResult(), equalTo(true));
        Assert.assertThat(spelRegex.isInvalidNumericStringResult(), equalTo(false));
        Assert.assertThat(spelRegex.isValidAlphabeticStringResult(), equalTo(true));
        Assert.assertThat(spelRegex.isInvalidAlphabeticStringResult(), equalTo(false));
        Assert.assertThat(spelRegex.isValidFormatOfHorsePower(), equalTo(true));
    }

    @Test
    public void testRelational() throws Exception {
        Assert.assertThat(spelRelational.isEqual(), equalTo(true));
        Assert.assertThat(spelRelational.isEqualAlphabetic(), equalTo(true));
        Assert.assertThat(spelRelational.isNotEqual(), equalTo(false));
        Assert.assertThat(spelRelational.isNotEqualAlphabetic(), equalTo(false));
        Assert.assertThat(spelRelational.isLessThan(), equalTo(false));
        Assert.assertThat(spelRelational.isLessThanAlphabetic(), equalTo(false));
        Assert.assertThat(spelRelational.isLessThanOrEqual(), equalTo(true));
        Assert.assertThat(spelRelational.isLessThanOrEqualAlphabetic(), equalTo(true));
        Assert.assertThat(spelRelational.isGreaterThan(), equalTo(false));
        Assert.assertThat(spelRelational.isGreaterThanAlphabetic(), equalTo(false));
        Assert.assertThat(spelRelational.isGreaterThanOrEqual(), equalTo(true));
        Assert.assertThat(spelRelational.isGreaterThanOrEqualAlphabetic(), equalTo(true));
        Assert.assertThat(spelRelational.isAnd(), equalTo(true));
        Assert.assertThat(spelRelational.isAndAlphabetic(), equalTo(true));
        Assert.assertThat(spelRelational.isOr(), equalTo(true));
        Assert.assertThat(spelRelational.isOrAlphabetic(), equalTo(true));
        Assert.assertThat(spelRelational.isNot(), equalTo(false));
        Assert.assertThat(spelRelational.isNotAlphabetic(), equalTo(false));
    }
}
