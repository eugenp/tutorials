package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.list.PeopleList;
import com.baeldung.hexagonal.adapters.list.Person;
import com.baeldung.hexagonal.core.DataCreator;
import com.baeldung.hexagonal.ports.ContentTypeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) @SpringBootTest public class HexagonalArchitectureTests {

        @Autowired ContentTypeMapper<PeopleList> listMapper;

        @Autowired ContentTypeMapper<String> csvMapper;

        String mockData = DataCreator.createMockData();

        @Test public void shouldConvertJsonToPeopleList() {

                PeopleList peopleList = listMapper.mapContent(mockData);

                Person johnSmith = new Person("John Smith", "123 456 789");
                assertThat(peopleList.getPeople().size()).isEqualTo(3);
                assertThat(peopleList.getPeople()).contains(johnSmith);

        }

        @Test public void shouldConvertJsonToCSV() {
                String csv = csvMapper.mapContent(mockData);
                String expectedCsv = "name; phoneNumber\n" + "John Smith;123 456 789\n" + "James Johnson;321 654 9879\n" + "Robert McCarry;654 456 789";
                assertThat(csv).isEqualTo(expectedCsv);
        }

}
