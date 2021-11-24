package com.baeldung.assertj;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Condition;
import org.junit.Test;

public class AssertJConditionUnitTest {
    private Condition<Member> senior = new Condition<>(m -> m.getAge() >= 60, "senior");
    private Condition<Member> nameJohn = new Condition<>(m -> m.getName().equalsIgnoreCase("John"), "name John");

    @Test
    public void whenUsingMemberAgeCondition_thenCorrect() {
        Member member = new Member("John", 65);
        assertThat(member).is(senior);

        try {
            assertThat(member).isNot(senior);
            fail();
        } catch (AssertionError e) {
            assertThat(e).hasMessageContaining("not to be senior");
        }
    }

    @Test
    public void whenUsingMemberNameCondition_thenCorrect() {
        Member member = new Member("Jane", 60);
        assertThat(member).doesNotHave(nameJohn);

        try {
            assertThat(member).has(nameJohn);
            fail();
        } catch (AssertionError e) {
            assertThat(e).hasMessageContaining("name John");
        }
    }

    @Test
    public void whenCollectionConditionsAreSatisfied_thenCorrect() {
        List<Member> members = new ArrayList<>();
        members.add(new Member("Alice", 50));
        members.add(new Member("Bob", 60));

        assertThat(members).haveExactly(1, senior);
        assertThat(members).doNotHave(nameJohn);
    }

    @Test
    public void whenCombiningAllOfConditions_thenCorrect() {
        Member john = new Member("John", 60);
        Member jane = new Member("Jane", 50);

        assertThat(john).is(allOf(senior, nameJohn));
        assertThat(jane).is(allOf(not(nameJohn), not(senior)));
    }

    @Test
    public void whenCombiningAnyOfConditions_thenCorrect() {
        Member john = new Member("John", 50);
        Member jane = new Member("Jane", 60);

        assertThat(john).is(anyOf(senior, nameJohn));
        assertThat(jane).is(anyOf(nameJohn, senior));
    }
}
