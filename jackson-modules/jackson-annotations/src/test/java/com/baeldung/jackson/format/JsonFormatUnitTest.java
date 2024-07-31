package com.baeldung.jackson.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.data.Percentage.withPercentage;

public class JsonFormatUnitTest {

    private static final String JSON_STRING = "{\"FIRSTNAME\":\"John\",\"lastname\":\"Smith\",\"cReAtEdDaTe\":\"2016-12-18@07:53:34.740+0000\"}";

    @Test
    public void whenSerializedDateFormat_thenCorrect() throws JsonProcessingException {

        User user = new User("Jay", "Sridhar");

        String result = new ObjectMapper().writeValueAsString(user);

        // Expected to match: "2016-12-19@09:34:42.628+0000"
        assertThat(from(result).getString("createdDate")).matches("\\d{4}\\-\\d{2}\\-\\d{2}@\\d{2}:\\d{2}:\\d{2}\\.\\d{3}\\+\\d{4}");

        // Expected to be close to current time
        long now = new Date().getTime();
        assertThat(from(result).getLong("dateNum")).isCloseTo(now, withPercentage(10.0));
    }

    @Test
    public void whenDeserializeJsonStrToUserObject_thenFail() {
        assertThatThrownBy(() -> new ObjectMapper().readValue(JSON_STRING, User.class)).isInstanceOf(UnrecognizedPropertyException.class);
    }

    @Test
    public void whenDeserializeJsonStrToUserIgnoreCaseObject_thenSuccess() throws JsonProcessingException, ParseException {
        UserIgnoreCase result = new ObjectMapper().readValue(JSON_STRING, UserIgnoreCase.class);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzz");
        Date expectedDate = fmt.parse("2016-12-18T07:53:34.740+0000");

        assertThat(result)
            .isNotNull()
            .returns("John", from(UserIgnoreCase::getFirstName))
            .returns("Smith", from(UserIgnoreCase::getLastName))
            .returns(expectedDate, from(UserIgnoreCase::getCreatedDate));
    }

}