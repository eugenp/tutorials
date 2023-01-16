
import com.parse.parsingdates.SimpleDateTimeFormat;
import com.parse.parsingdates.SimpleDateTimeFormater;
import com.parse.parsingdates.SimpleDateUtils;
import com.parse.parsingdates.SimpleParseDate;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.assertEquals;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class SimpleParseDateTest {

    @Test
    public void testDateParse() {
        SimpleParseDate simpleParseDate = new SimpleParseDate();
        String date = "2022-40-40";
        Assert.assertNotEquals(simpleParseDate.parseDate(date, Arrays.asList("MM/dd/yyyy", "dd.MM.yyyy", "yyyy-MM-dd")), null); // true
        assertEquals(simpleParseDate.parseDateWithMatcher(date), null); // true
    }

    @Test
    public void testSimpleDateTimeParse() {
        SimpleDateTimeFormater simpleDateTimeFormater = new SimpleDateTimeFormater();
        assertEquals(simpleDateTimeFormater.isDateValid("2022-12-04"), true);// true
        assertEquals(simpleDateTimeFormater.isDateValid("2022-13-04"), false);// true
    }

    @Test
    public void testDateUtils() {
        SimpleDateUtils simpleDateUtils = new SimpleDateUtils();
        assertEquals(simpleDateUtils.isDateValid("53/10/2014"), false); // true
        assertEquals(simpleDateUtils.isDateValid("10/10/2014"), true); // true
    }

    @Test
    public void testJodaTime() {
        SimpleDateTimeFormat simpleDateUtils = new SimpleDateTimeFormat();
        assertEquals(simpleDateUtils.isDateValid("53/10/2014"), false); // true
        assertEquals(simpleDateUtils.isDateValid("10/10/2014"), true); // true
    }

}
