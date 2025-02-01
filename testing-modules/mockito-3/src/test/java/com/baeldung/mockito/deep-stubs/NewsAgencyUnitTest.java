import com.baeldung.mockito.deepstubs.NewsAgency;
import com.baeldung.mockito.deepstubs.NewsArticle;
import com.baeldung.mockito.deepstubs.Reporter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewsAgencyUnitTest {
    @Test
    void whenInitializingAllObjects_thenGetAllArticlesTest(){
        //Given
        String title1 = "new study reveals the dimension where the single socks disappear";
        NewsArticle article1 = new NewsArticle(title1,"link1");
        Reporter reporter1 = new Reporter("Tom", article1);
        String title2 = "secret meeting of cats union against vacuum cleaners";
        NewsArticle article2 = new NewsArticle(title2,"link2");
        Reporter reporter2 = new Reporter("Maria", article2);
        List<String> expectedResults = List.of(title1, title2);
        NewsAgency newsAgency = new NewsAgency(List.of(reporter1, reporter2));

        //When
        List<String> actualResults = newsAgency.getLatestArticlesNames();

        //Then
        assertEquals(expectedResults, actualResults);
    }

    @Test
    void givenInitializeUsingMocksAndDeepStubs_whenCallingGetAllArticles_thenGetAllArticlesTest(){
        //Given
        Reporter mockReporter1 = mock(Reporter.class, Mockito.RETURNS_DEEP_STUBS);
        String title1 = "cow flying in London, royal guard still did not move";
        when(mockReporter1.getLatestArticle().getName()).thenReturn(title1);
        Reporter mockReporter2 = mock(Reporter.class, Mockito.RETURNS_DEEP_STUBS);
        String title2 = "drunk man accidentally runs for mayor and wins";
        when(mockReporter2.getLatestArticle().getName()).thenReturn(title2);
        NewsAgency newsAgency = new NewsAgency(List.of(mockReporter1, mockReporter2));
        List<String> expectedResults = List.of(title1, title2);

        //When
        List<String> actualResults = newsAgency.getLatestArticlesNames();

        //Then
        assertEquals(actualResults, expectedResults);
    }

}
