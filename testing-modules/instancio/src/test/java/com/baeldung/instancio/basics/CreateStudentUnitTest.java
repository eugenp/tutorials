package com.baeldung.instancio.basics;

import com.baeldung.instancio.student.model.Address;
import com.baeldung.instancio.student.model.ContactInfo;
import com.baeldung.instancio.student.model.Course;
import com.baeldung.instancio.student.model.Grade;
import com.baeldung.instancio.student.model.Phone;
import com.baeldung.instancio.student.model.Student;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.InstancioSource;
import org.instancio.junit.Seed;
import org.instancio.junit.WithSettings;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.field;

/**
 * Sample test class using Instancio to generate test objects.
 *
 * <p>Note: using {@link InstancioExtension} is optional. The extension adds support for:
 * <p>
 * - reporting seed value if a test fails
 * - {@link Seed} annotation for reproducing failed tests
 * - {@link WithSettings} for injecting custom settings, if needed
 */
@ExtendWith(InstancioExtension.class)
class CreateStudentUnitTest {

    /**
     * Common settings to be used by all test methods.
     */
    @WithSettings
    private static final Settings settings = Settings.create()
            .set(Keys.COLLECTION_MAX_SIZE, 3);

    /**
     * A {@link Model} is a template for creating objects.
     * Objects created from a model can be created as is, or customized, if needed.
     */
    private static Model<Student> studentModel() {
        return Instancio.of(Student.class)
                .generate(field(Student::getDateOfBirth), gen -> gen.temporal().localDate().past())
                .generate(field(Student::getEnrollmentYear), gen -> gen.temporal().year().past())
                .generate(field(ContactInfo::getEmail), gen -> gen.text().pattern("#a#a#a#a#a#a@example.com"))
                .generate(field(Phone::getCountryCode), gen -> gen.string().prefix("+").digits().maxLength(2))
                .withNullable(field(Student::getEmergencyContact))
                .toModel();
    }

    private static void assertModelProperties(Student student) {
        assertThat(student.getDateOfBirth()).isBefore(LocalDate.now());
        assertThat(student.getEnrollmentYear()).isLessThan(Year.now());
        assertThat(student.getContactInfo().getEmail()).matches("^[a-zA-Z0-9]+@example.com$");
        assertThat(student.getContactInfo().getPhones())
                .extracting(Phone::getCountryCode)
                .allSatisfy(countryCode -> assertThat(countryCode).matches("^\\+\\d\\d?$"));
    }

    /**
     * Generates random Student objects based on the Model.
     */
    @Test
    void whenGivenAModel_thenShouldCreateAStudentBasedOnModel() {
        Student student = Instancio.create(studentModel());

        assertModelProperties(student);
    }

    /**
     * Generate a list of international students based on the Model.
     */
    @Test
    void whenGivenAModel_thenShouldCreateAListOfStudents() {
        // Given
        final int numberOfStudents = 100;
        final List<String> countries = Arrays.asList(
                "China", "Germany", "India", "Poland", "Romania", "Sweden", "Switzerland");

        // When
        List<Student> studentList = Instancio.ofList(studentModel())
                .size(numberOfStudents)
                .generate(field(Address::getCountry), gen -> gen.oneOf(countries))
                .create();

        // Then
        assertThat(studentList).hasSize(numberOfStudents)
                .allSatisfy(CreateStudentUnitTest::assertModelProperties)
                .extracting(student -> student.getContactInfo().getAddress().getCountry())
                .allSatisfy(country -> assertThat(country).isIn(countries));
    }

    /**
     * Use the Model to create a student with a failed course.
     * This test also demonstrates how Instancio can provide
     * arguments to parameterized tests.
     *
     * @param failedCourse provided by Instancio
     */
    @InstancioSource
    @ParameterizedTest
    void whenGivenFailingGrade_thenStudentShouldHaveAFailedCourse(final Course failedCourse) {
        // Given
        final Model<Student> model = studentModel();
        final Grade failingGrade = Grade.F;

        // When
        Student student = Instancio.of(model)
                .generate(field(Student::getCourseGrades), gen -> gen.map().with(failedCourse, failingGrade))
                .create();

        // Then
        Map<Course, Grade> courseGrades = student.getCourseGrades();
        assertModelProperties(student);
        assertThat(courseGrades).containsEntry(failedCourse, failingGrade);
    }

    /**
     * Generate a student with only Grades A and/or B.
     */
    @Test
    void whenGivenGoodGrades_thenCreatedStudentShouldHaveExpectedGrades() {
        // Given
        final int numOfCourses = 10;
        final Grade[] grades = {Grade.A, Grade.B};

        // When
        Student student = Instancio.of(studentModel())
                .generate(all(Grade.class), gen -> gen.oneOf(grades))
                .generate(field(Student::getCourseGrades), gen -> gen.map().size(numOfCourses))
                .create();

        // Then
        Map<Course, Grade> courseGrades = student.getCourseGrades();
        assertModelProperties(student);
        assertThat(courseGrades.values())
                .hasSize(numOfCourses)
                .containsAnyOf(grades)
                .doesNotContain(Grade.C, Grade.D, Grade.F);
    }

    /**
     * Generate String fields prefixed with the field's name.
     */
    @Test
    void whenGivenCustomSettings_thenStudentShouldBeCreatedUsingTheSettings() {
        // Given
        Settings customSettings = Settings.create()
                .set(Keys.STRING_FIELD_PREFIX_ENABLED, true);

        // When
        Student student = Instancio.of(studentModel())
                .withSettings(customSettings)
                .create();

        // Then
        assertThat(student.getFirstName()).startsWith("firstName_");
        assertThat(student.getLastName()).startsWith("lastName_");
        assertThat(student.getContactInfo().getAddress().getCity()).startsWith("city_");
    }
}
