package com.baeldung.modelassert;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import uk.org.webcompere.modelassert.json.dsl.nodespecific.tree.WhereDsl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.org.webcompere.modelassert.json.JsonAssertions.*;
import static uk.org.webcompere.modelassert.json.PathWildCard.ANY;
import static uk.org.webcompere.modelassert.json.PathWildCard.ANY_SUBTREE;
import static uk.org.webcompere.modelassert.json.Patterns.GUID_PATTERN;

class ModelAssertUnitTest {
    private static final String ACTUAL_JSON = "{" +
        "\"name\": \"Baeldung\"," +
        "\"isOnline\": true," +
        "\"topics\": [ \"Java\", \"Spring\", \"Kotlin\", \"Scala\", \"Linux\" ]" +
        "}";

    private static final Path PATH_TO_EXPECTED = Paths.get("src", "test", "resources", "modelassert", "baeldung.json");

    public interface DataService {

        boolean isUserLoggedIn(String userDetails);
    }

    private DataService mockDataService = mock(DataService.class);

    @Test
    void givenJson_thenNameIsBaeldung() {
        assertJson(ACTUAL_JSON)
          .at("/name").isText("Baeldung");
    }

    @Test
    void givenJson_thenSecondTopicIsSpring() {
        assertJson(ACTUAL_JSON)
          .at("/topics/1").isText("Spring");
    }

    @Test
    void givenJson_thenCanMakeMultipleAssertions() {
        assertJson(ACTUAL_JSON)
          .at("/name").isText("Baeldung")
          .at("/topics/1").isText("Spring");
    }

    @Test
    void givenCompareByString_thenFailsOnFormatting() throws IOException {
        String expected = String.join("\n", Files.readAllLines(PATH_TO_EXPECTED));

        assertThatThrownBy(() -> assertThat(ACTUAL_JSON).isEqualTo(expected))
          .isInstanceOf(AssertionFailedError.class);
    }

    @Test
    void givenTwoIdenticalTreesInDifferentFormat_thenPassesWithIsEqualTo() {
        assertJson(ACTUAL_JSON)
          .isEqualTo(PATH_TO_EXPECTED);
    }

    @Test
    void givenMap_thenCanCompareToYaml() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");

        assertJson(map)
          .isEqualToYaml("name: baeldung");
    }

    @Test
    void givenYaml_thenCanCompareToMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");

        assertYaml("name: baeldung")
          .isEqualTo(map);
    }

    @Test
    void canProduceHamcrestMatcher() {
        Matcher<String> matcher = json().at("/name").hasValue("Baeldung");

        MatcherAssert.assertThat(ACTUAL_JSON, matcher);
    }

    @Test
    void givenJson_thenCanAssertWithMatcherAssert() {

        MatcherAssert.assertThat(ACTUAL_JSON, json()
          .at("/name").hasValue("Baeldung")
          .at("/topics/1").isText("Spring"));
    }

    @Test
    void givenUserIsOnline_thenIsLoggedIn() {
        given(mockDataService.isUserLoggedIn(argThat(json()
          .at("/isOnline").isTrue()
          .toArgumentMatcher())))
          .willReturn(true);

        assertThat(mockDataService.isUserLoggedIn(ACTUAL_JSON))
          .isTrue();

        verify(mockDataService)
          .isUserLoggedIn(argThat(json()
            .at("/name").isText("Baeldung")
            .toArgumentMatcher()));
    }

    @Test
    void givenDocument_canMakeSeveralRootNodeAssertions() {
        assertJson(ACTUAL_JSON)
          .isNotNull()
          .isNotNumber()
          .isObject()
          .containsKey("name");
    }

    @Test
    void givenDocument_canAssertArray() {
        assertJson(ACTUAL_JSON)
          .at("/topics").hasSize(5);
    }

    @Test
    void givenDocument_canAssertBooleanByType() {
        assertJson(ACTUAL_JSON)
          .at("/isOnline").booleanNode().isTrue();
    }

    @Test
    void givenTextNode_canAssertContents() {
        assertJson(ACTUAL_JSON)
          .at("/name").textContains("ael");
    }

    @Test
    void givenTextNode_canMatchWithRegex() {
        assertJson(ACTUAL_JSON)
          .at("/name").matches("[A-Z].+");
    }

    @Test
    void givenNumberNode_canTestRange() {
        assertJson("{count: 12}")
          .at("/count").isBetween(1, 25);
    }

    @Test
    void givenNumberNode_canTestDouble() {
        assertJson("{height: 6.3}")
          .at("/height").isGreaterThanDouble(6.0);
    }

    @Test
    void givenNumberNode_canTestDoubleEquals() {
        assertJson("{height: 6.3}")
          .at("/height").isNumberEqualTo(6.3);
    }

    @Test
    void givenArrayNode_canTestContains() {
        assertJson(ACTUAL_JSON)
          .at("/topics").isArrayContaining("Scala", "Spring");
    }

    @Test
    void givenArrayNode_canTestContainsExactlyInAnyOrder() {
        assertJson(ACTUAL_JSON)
          .at("/topics").isArrayContainingExactlyInAnyOrder("Scala", "Spring", "Java", "Linux", "Kotlin");
    }

    @Test
    void givenArrayNode_canTestContainsExactly() {
        assertJson(ACTUAL_JSON)
          .at("/topics").isArrayContainingExactly("Java", "Spring", "Kotlin", "Scala", "Linux");
    }

    @Test
    void givenArrayNode_thenCanAssertBySubtree() {
        assertJson(ACTUAL_JSON)
          .at("/topics").isEqualTo("[ \"Java\", \"Spring\", \"Kotlin\", \"Scala\", \"Linux\" ]");
    }

    @Test
    void givenKeyOrderIsDifferent_canStillBeEqual() {
        String actualJson = "{a:{d:3, c:2, b:1}}";
        String expectedJson = "{a:{b:1, c:2, d:3}}";

        assertJson(actualJson)
          .where().keysInAnyOrder()
          .isEqualTo(expectedJson);
    }

    @Test
    void givenKeyOrderIsDifferentInA_canStillBeEqual() {
        String actualJson = "{a:{d:3, c:2, b:1}}";
        String expectedJson = "{a:{b:1, c:2, d:3}}";

        assertJson(actualJson)
          .where()
              .at("/a").keysInAnyOrder()
          .isEqualTo(expectedJson);
    }

    @Test
    void givenElementOrderIsDifferent_canStillBeEqual() {
        String actualJson = "{a:[1, 2, 3, 4, 5]}";
        String expectedJson = "{a:[5, 4, 3, 2, 1]}";

        assertJson(actualJson)
          .where().arrayInAnyOrder()
          .isEqualTo(expectedJson);
    }

    @Test
    void givenActualHasExtraValue_thenIgnoreIt() {
        String actualJson = "{user:{name: \"Baeldung\", url:\"http://www.baeldung.com\"}}";
        String expectedJson = "{user:{name: \"Baeldung\"}}";

        assertJson(actualJson)
          .where().at("/user/url").isIgnored()
          .isEqualTo(expectedJson);
    }

    @Test
    void givenActualHasIdGUIDS_thenIgnoreThem() {
        String actualJson = "{user:{credentials:[" +
          "{id:\"a7dc2567-3340-4a3b-b1ab-9ce1778f265d\",role:\"Admin\"}," +
          "{id:\"09da84ba-19c2-4674-974f-fd5afff3a0e5\",role:\"Sales\"}]}}";
        String expectedJson = "{user:{credentials:" +
          "[{id:\"???\",role:\"Admin\"}," +
          "{id:\"???\",role:\"Sales\"}]}}";

        assertJson(actualJson)
          .where()
            .path("user","credentials", ANY, "id").isIgnored()
          .isEqualTo(expectedJson);
    }

    @Test
    void givenActualHasIdGUIDS_thenMatchThemByRegexAndSubtree() {
        String actualJson = "{user:{credentials:[" +
          "{id:\"a7dc2567-3340-4a3b-b1ab-9ce1778f265d\",role:\"Admin\"}," +
          "{id:\"09da84ba-19c2-4674-974f-fd5afff3a0e5\",role:\"Sales\"}]}}";
        String expectedJson = "{user:{credentials:" +
          "[{id:\"???\",role:\"Admin\"}," +
          "{id:\"???\",role:\"Sales\"}]}}";

        assertJson(actualJson)
          .where()
          .path(ANY_SUBTREE, "id").matches(GUID_PATTERN)
          .isEqualTo(expectedJson);
    }

    @Test
    void givenActualHasIdGUIDS_thenMatchThemByRegexAndSubtreeUsingStandardPattern() {
        String actualJson = "{user:{credentials:[" +
          "{id:\"a7dc2567-3340-4a3b-b1ab-9ce1778f265d\",role:\"Admin\"}," +
          "{id:\"09da84ba-19c2-4674-974f-fd5afff3a0e5\",role:\"Sales\"}]}}";
        String expectedJson = "{user:{credentials:" +
          "[{id:\"???\",role:\"Admin\"}," +
          "{id:\"???\",role:\"Sales\"}]}}";

        assertJson(actualJson)
          .where()
          .configuredBy(where -> idsAreGuids(where))
          .isEqualTo(expectedJson);
    }

    private static <T> WhereDsl<T> idsAreGuids(WhereDsl<T> where) {
        return where.path(ANY_SUBTREE, "id").matches(GUID_PATTERN);
    }
}
