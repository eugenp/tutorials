package com.baeldung.stringtemplates;

import org.junit.Assert;
import org.junit.Test;

public class StringTemplatesUnitTest {

    @Test
    public void whenStringConcat_thenReturnComposedString() {
        StringCompositionTechniques stringCompositionTechniques = new StringCompositionTechniques();
        Assert.assertEquals("Today's weather is pleasant, with a temperature of 25 degrees Celsius", stringCompositionTechniques.composeUsingPlus("pleasant", "25", "Celsius"));
    }

    @Test
    public void whenStringBuffer_thenReturnComposedString() {
        StringCompositionTechniques stringCompositionTechniques = new StringCompositionTechniques();
        Assert.assertEquals("Today's weather is pleasant, with a temperature of 25 degrees Celsius", stringCompositionTechniques.composeUsingStringBuffer("pleasant", "25", "Celsius"));
    }

    @Test
    public void whenStringBuilder_thenReturnComposedString() {
        StringCompositionTechniques stringCompositionTechniques = new StringCompositionTechniques();
        Assert.assertEquals("Today's weather is pleasant, with a temperature of 25 degrees Celsius", stringCompositionTechniques.composeUsingStringBuilder("pleasant", "25", "Celsius"));
    }

    @Test
    public void whenStringFormatter_thenReturnComposedFormattedString() {
        StringCompositionTechniques stringCompositionTechniques = new StringCompositionTechniques();
        Assert.assertEquals("Today's weather is pleasant, with a temperature of 25 degrees Celsius", stringCompositionTechniques.composeUsingFormatters("pleasant", "25", "Celsius"));
    }

    @Test
    public void whenMessageFormatter_thenReturnComposedFormattedString() {
        StringCompositionTechniques stringCompositionTechniques = new StringCompositionTechniques();
        Assert.assertEquals("Today's weather is pleasant, with a temperature of 25 degrees Celsius", stringCompositionTechniques.composeUsingMessageFormatter("pleasant", "25", "Celsius"));
    }

    @Test
    public void whenUsingStringTemplateSTR_thenReturnInterpolatedString() {
        StringTemplateExamples templateExamples = new StringTemplateExamples();
        Assert.assertEquals("Today's weather is pleasant, with a temperature of 25 degrees Celsius", templateExamples.interpolationUsingSTRProcessor("pleasant", "25", "Celsius"));
    }

    @Test
    public void whenUsingMultilineStringTemplateSTR_thenReturnInterpolatedString() {
        StringTemplateExamples templateExamples = new StringTemplateExamples();
        Assert.assertEquals("{\n" + "  \"feelsLike\": \"pleasant\",\n" + "  \"temperature\": \"25\",\n" + "  \"unit\": \"Celsius\"\n" + "}\n", templateExamples.interpolationOfJSONBlock("pleasant", "25", "Celsius"));
    }

    @Test
    public void whenUsingExpressionSTR_thenReturnInterpolatedString() {
        StringTemplateExamples templateExamples = new StringTemplateExamples();
        Assert.assertEquals("Today's weather is pleasant, with a temperature of 25 degrees Celsius", templateExamples.interpolationWithExpressions());
    }

    @Test
    public void whenUsingExpressionRAW_thenReturnInterpolatedString() {
        StringTemplateExamples templateExamples = new StringTemplateExamples();
        Assert.assertEquals("Today's weather is pleasant, with a temperature of 25 degrees Celsius", templateExamples.interpolationWithTemplates());
    }

    @Test
    public void whenUsingExpressionFMT_thenReturnInterpolatedString() {
        StringTemplateExamples templateExamples = new StringTemplateExamples();
        Assert.assertEquals("{\n" + "  \"feelsLike\": \"pleasant\",\n" + "  \"temperature\": \"25.86\",\n" + "  \"unit\": \"Celsius\"\n" + "}\n", templateExamples.interpolationOfJSONBlockWithFMT("pleasant", 25.8636F, "Celsius"));
    }
}