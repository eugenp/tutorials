package com.baeldung.serenity.membership;

import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Step;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MemberStatusSteps {

    private Member member;

    @Step("Given a member has {0} points")
    public void aMemberHasPointsOf(int points) {
        member = Member.withInitialPoints(points);
    }

    @Step("Then the member grade should be {0}")
    public void theMemberShouldHaveAStatusOf(MemberGrade grade) {
        assertThat(member.getGrade(), equalTo(grade));
    }

    @Step("When the member spends ${0} ")
    public void theMemberSpends(int moneySpent) {
        member.spend(moneySpent);
    }

    @Step("Given client joins membership program")
    public void aClientJoinsTheMemberProgram() {
        member = Member.withInitialPoints(0);
    }

    @Pending
    @Step("When the member exchange {}")
    public void aMemberExchangeA(Commodity commodity) {
        // TODO
    }

    @Pending
    @Step("Then the member should have points left")
    public void memberShouldHavePointsLeft() {

    }
}
