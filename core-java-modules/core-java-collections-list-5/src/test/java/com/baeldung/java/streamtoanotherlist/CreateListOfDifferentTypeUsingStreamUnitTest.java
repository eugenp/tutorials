package com.baeldung.java.streamtoanotherlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import lombok.Getter;

@Getter
class Employee {
    private final String name;
    private final Set<String> hobbies = new HashSet<>();
    private final String email;
    private String department;

    public Employee(String name, String email, Collection<String> hobbies) {
        this.name = name;
        this.email = email;
        this.hobbies.addAll(hobbies);
    }
}

class TennisPlayerCandidate {
    private final String name;
    private final String email;
    private final Boolean confirmed = Boolean.FALSE;

    public TennisPlayerCandidate(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TennisPlayerCandidate))
            return false;

        TennisPlayerCandidate that = (TennisPlayerCandidate) o;

        if (!Objects.equals(name, that.name))
            return false;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}

public class CreateListOfDifferentTypeUsingStreamUnitTest {
    private final static List<Employee> EMPLOYEES = Lists.newArrayList(
      // @formatter:off
      new Employee("Kai", "kai@company.com", Lists.newArrayList("Football", "Reading", "Chess")),
      new Employee("Eric", "eric@company.com", Lists.newArrayList("Tennis", "Baseball", "Singing")),
      new Employee("Saajan", "saajan@company.com", Lists.newArrayList("Tennis", "Baseball", "Singing")),
      new Employee("Kevin", "kevin@company.com", Lists.newArrayList("Dancing", "Computer Games", "Tennis")),
      new Employee("Amanda", "amanda@company.com", Lists.newArrayList("Painting", "Yoga", "Dancing"))
      //@formatter:on
    );

    private final static List<TennisPlayerCandidate> EXPECTED = Lists.newArrayList(
      // @formatter:off
      new TennisPlayerCandidate("Eric", "eric@company.com"),
      new TennisPlayerCandidate("Saajan", "saajan@company.com"),
      new TennisPlayerCandidate("Kevin", "kevin@company.com")
      //@formatter:on
    );

    @Test
    void whenUsingStreamForEachFillingAnotherList_thenGetExpectedResult() {
        List<TennisPlayerCandidate> result = new ArrayList<>();
        EMPLOYEES.forEach(e -> {
            if (e.getHobbies()
              .contains("Tennis")) {
                result.add(new TennisPlayerCandidate(e.getName(), e.getEmail()));
            }
        });

        assertEquals(EXPECTED, result);
    }

    @Test
    void whenUsingStreamMap_thenGetExpectedResult() {
        List<TennisPlayerCandidate> result = EMPLOYEES.stream()
          .filter(e -> e.getHobbies()
            .contains("Tennis"))
          .map(e -> new TennisPlayerCandidate(e.getName(), e.getEmail()))
          .collect(Collectors.toList());
        assertEquals(EXPECTED, result);
    }

    @Test
    void whenUsingCollectorMapping_thenGetExpectedResult() {
        List<TennisPlayerCandidate> result = EMPLOYEES.stream()
          .filter(e -> e.getHobbies()
            .contains("Tennis"))
          .collect(Collectors.mapping(e -> new TennisPlayerCandidate(e.getName(), e.getEmail()), Collectors.toList()));
        assertEquals(EXPECTED, result);
    }
}