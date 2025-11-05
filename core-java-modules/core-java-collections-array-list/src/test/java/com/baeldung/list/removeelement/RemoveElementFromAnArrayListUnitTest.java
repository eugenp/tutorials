package com.baeldung.list.removeelement;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RemoveElementFromAnArrayListUnitTest {
    private List<String> getSportList() {
        List<String> sports = new ArrayList<>();
        sports.add("Football");
        sports.add("Basketball");
        sports.add("Baseball");
        sports.add("Boxing");
        sports.add("Cycling");
        return sports;
    }

    @Test
    void whenRemoveByIndex_thenCorrect() {
        List<String> sports = getSportList();
        assertThat(sports).hasSize(5).contains("Basketball");

        sports.remove(1); //removing Basketball
        assertThat(sports).hasSize(4).doesNotContain("Basketball");
    }

    @Test
    void whenRemoveLastByIndex_thenCorrect() {
        List<String> sports = getSportList();
        List<String> expected = List.of("Football", "Basketball", "Baseball", "Boxing");

        sports.remove(sports.size() - 1);
        assertThat(sports).isEqualTo(expected);
    }

    @Test
    void whenUsingJDK21RemoveLast_thenCorrect() {
        List<String> sports = getSportList();
        List<String> expected = List.of("Football", "Basketball", "Baseball", "Boxing");

        sports.removeLast();
        assertThat(sports).isEqualTo(expected);
    }


    @Test
    void whenRemoveByElement_thenCorrect() {
        List<String> sports = getSportList();
        assertThat(sports).hasSize(5).contains("Basketball");

        sports.remove("Basketball");
        assertThat(sports).hasSize(4).doesNotContain("Basketball");
    }

    @Test
    void whenRemovingWhileIterating_thenCorrect() {
        List<String> sports = getSportList();
        assertThat(sports).hasSize(5).contains("Basketball");

        Iterator<String> iterator = sports.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("Basketball")) {
                iterator.remove();
                break;
            }
        }
        assertThat(sports).hasSize(4).doesNotContain("Basketball");
    }

    @Test
    void whenRemoveIf_thenCorrect() {
        List<String> sports = getSportList();
        assertThat(sports).hasSize(5).contains("Basketball");

        sports.removeIf(p -> p.equals("Basketball"));

        assertThat(sports).hasSize(4).doesNotContain("Basketball");
    }
}