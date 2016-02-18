package com.baeldung.jsonpath.introduction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;

public class OperationTest {
    InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("intro_api.json");
    String jsonDataSourceString = new Scanner(jsonInputStream, "UTF-8").useDelimiter("\\Z").next();

    @Test
    public void givenJsonPathWithoutPredicates_whenReading_thenCorrect() {
        String jsonpathCreatorNamePath = "$['tool']['jsonpath']['creator']['name']";
        String jsonpathCreatorLocationPath = "$['tool']['jsonpath']['creator']['location'][*]";

        DocumentContext jsonContext = JsonPath.parse(jsonDataSourceString);
        String jsonpathCreatorName = jsonContext.read(jsonpathCreatorNamePath);
        List<String> jsonpathCreatorLocation = jsonContext.read(jsonpathCreatorLocationPath);

        assertEquals("Jayway Inc.", jsonpathCreatorName);
        assertThat(jsonpathCreatorLocation.toString(), containsString("Malmo"));
        assertThat(jsonpathCreatorLocation.toString(), containsString("San Francisco"));
        assertThat(jsonpathCreatorLocation.toString(), containsString("Helsingborg"));
    }

    @Test
    public void givenJsonPathWithFilterPredicate_whenReading_thenCorrect() {
        Filter expensiveFilter = Filter.filter(Criteria.where("price").gt(20.00));
        List<Map<String, Object>> expensive = JsonPath.parse(jsonDataSourceString).read("$['book'][?]", expensiveFilter);
        predicateUsageAssertionHelper(expensive);
    }

    @Test
    public void givenJsonPathWithCustomizedPredicate_whenReading_thenCorrect() {
        Predicate expensivePredicate = new Predicate() {
            public boolean apply(PredicateContext context) {
                String value = context.item(Map.class).get("price").toString();
                return Float.valueOf(value) > 20.00;
            }
        };
        List<Map<String, Object>> expensive = JsonPath.parse(jsonDataSourceString).read("$['book'][?]", expensivePredicate);
        predicateUsageAssertionHelper(expensive);
    }

    @Test
    public void givenJsonPathWithInlinePredicate_whenReading_thenCorrect() {
        List<Map<String, Object>> expensive = JsonPath.parse(jsonDataSourceString).read("$['book'][?(@['price'] > $['price range']['medium'])]");
        predicateUsageAssertionHelper(expensive);
    }

    private void predicateUsageAssertionHelper(List<?> predicate) {
        assertThat(predicate.toString(), containsString("Beginning JSON"));
        assertThat(predicate.toString(), containsString("JSON at Work"));
        assertThat(predicate.toString(), not(containsString("Learn JSON in a DAY")));
        assertThat(predicate.toString(), not(containsString("JSON: Questions and Answers")));
    }
}
