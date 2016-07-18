package com.baeldung.restassured;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.post;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.containsString;
import io.restassured.module.jsv.JsonSchemaValidator;
import static org.hamcrest.xml.HasXPath.hasXPath;

import org.junit.Test;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class RestAssuredTest {

	@Test
	public void givenJsonResponse_whenKeyValuePairMatches_thenCorrect() {
		JsonSchemaFactory factory = JsonSchemaFactory
				.newBuilder()
				.setValidationConfiguration(
						ValidationConfiguration.newBuilder()
						.setDefaultVersion(SchemaVersion.DRAFTV3)
						.freeze()).freeze();
		JsonSchemaValidator.settings = settings().with()
				.jsonSchemaFactory(factory).and().with()
				.checkedValidation(false);
	}

	@Test
	public void givenJsonArrayOfSimilarObjects_whenHasGivenValuesForGivenKey_thenCorrect() {

	}

	@Test
	public void givenUrl_whenSuccessOnGetsResponse_andJsonHasRequiredKV_thenCorrect() {

		get("/events?id=390").then().statusCode(200).assertThat()
		.body("data.id", equalTo(390));

	}

	@Test
	public void givenUrl_whenJsonResponseHasArrayWithGivenValuesUnderKey_thenCorrect() {

		get("/events?id=390").then().assertThat()
		.body("odds.price", hasItems("1.30", "5.25"));

	}

	@Test
	public void givenUrl_whenJsonResponseConformsToSchema_thenCorrect() {
		get("/events?id=390").then().assertThat()
		.body(matchesJsonSchemaInClasspath("event_0.json"));
	}

	@Test
	public void givenUrl_whenValidatesResponseWithInstanceSettings_thenCorrect() {
		JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory
				.newBuilder()
				.setValidationConfiguration(
						ValidationConfiguration.newBuilder()
						.setDefaultVersion(SchemaVersion.DRAFTV4)
						.freeze()).freeze();

		get("/events?id=390")
		.then()
		.assertThat()
		.body(matchesJsonSchemaInClasspath("event_0.json").using(
				jsonSchemaFactory));

	}

	@Test
	public void givenUrl_whenValidatesResponseWithStaticSettings_thenCorrect() {

		get("/events?id=390")
		.then()
		.assertThat()
		.body(matchesJsonSchemaInClasspath("event_0.json").using(
				settings().with().checkedValidation(false)));

	}

	@Test
	public void givenUrl_whenCheckingFloatValuePasses_thenCorrect() {
		get("/odd").then().assertThat().body("odd.ck", equalTo(12.2f));
	}

	@Test
	public void givenUrl_whenXmlResponseValueTestsEqual_thenCorrect() {
		post("/employees").then().assertThat()
		.body("employees.employee.first-name", equalTo("Jane"));
	}

	@Test
	public void givenUrl_whenMultipleXmlValuesTestEqual_thenCorrect() {
		post("/employees").then().assertThat()
		.body("employees.employee.first-name", equalTo("Jane"))
		.body("employees.employee.last-name", equalTo("Daisy"))
		.body("employees.employee.sex", equalTo("f"));
	}

	@Test
	public void givenUrl_whenMultipleXmlValuesTestEqualInShortHand_thenCorrect() {
		post("/employees")
		.then()
		.assertThat()
		.body("employees.employee.first-name", equalTo("Jane"),
				"employees.employee.last-name", equalTo("Daisy"),
				"employees.employee.sex", equalTo("f"));
	}

	@Test
	public void givenUrl_whenValidatesXmlUsingXpath_thenCorrect() {
		post("/employees")
		.then()
		.assertThat()
		.body(hasXPath("/employees/employee/first-name",
				containsString("Ja")));

	}

	@Test
	public void givenUrl_whenValidatesXmlUsingXpath2_thenCorrect() {
		post("/employees")
		.then()
		.assertThat()
		.body(hasXPath("/employees/employee/first-name[text()='Jane']"));

	}

	@Test
	public void givenUrl_whenVerifiesScienceTeacherFromXml_thenCorrect() {
		get("/teachers")
		.then()
		.body("teachers.teacher.find { it.@department == 'science' }.subject",
				hasItems("math", "physics"));
	}

	@Test
	public void givenUrl_whenVerifiesOddPricesAccuratelyByStatus_thenCorrect() {
		get("/odds").then().body("odds.findAll { it.status > 0 }.price",
				hasItems(1.30f, 1.20f));
	}

}
