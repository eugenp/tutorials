package com.baeldung.serenity;

import com.baeldung.serenity.membership.MemberStatusSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.baeldung.serenity.membership.Commodity.MacBookPro;
import static com.baeldung.serenity.membership.MemberGrade.Bronze;
import static com.baeldung.serenity.membership.MemberGrade.Gold;
import static com.baeldung.serenity.membership.MemberGrade.Silver;

@RunWith(SerenityRunner.class)
public class MemberStatusManualTest {

    @Steps
    private MemberStatusSteps memberSteps;

    @Test
    public void membersShouldStartWithBronzeStatus() {
        memberSteps.aClientJoinsTheMemberProgram();
        memberSteps.theMemberShouldHaveAStatusOf(Bronze);
    }

    @Test
    @Title("Members earn Silver grade after 1000 points ($10,000)")
    public void earnsSilverAfterSpends$10000() {
        memberSteps.aClientJoinsTheMemberProgram();
        memberSteps.theMemberSpends(10_000);
        memberSteps.theMemberShouldHaveAStatusOf(Silver);
    }

    @Test
    @Title("Members with 2,000 points should earn Gold grade when added 3,000 points ($30,000)")
    public void memberWith2000PointsEarnsGoldAfterSpends$30000() {
        memberSteps.aMemberHasPointsOf(2000);
        memberSteps.theMemberSpends(30_000);
        memberSteps.theMemberShouldHaveAStatusOf(Gold);
    }

    @Test
    @Title("Members with 50,000 points can exchange a MacBook Pro")
    public void memberWith50000PointsCanExchangeAMacbookpro() {
        memberSteps.aMemberHasPointsOf(50_000);
        memberSteps.aMemberExchangeA(MacBookPro);
        memberSteps.memberShouldHavePointsLeft();
    }

    /**
     * This test should fail, comment out <code>@Ignore</code> to see how failed test can be reflected in Serenity report. <br/>
     */
    @Test
    @Ignore
    @Title("Members with 500 points should have a Gold status when added 4,000 points ($40,000)")
    public void memberWith500PointsEarnsGoldAfterSpends$40000() {
        memberSteps.aMemberHasPointsOf(500);
        memberSteps.theMemberSpends(40_000);
        memberSteps.theMemberShouldHaveAStatusOf(Gold);
    }

    @Test
    @Ignore
    @Title("Members with 100 points would have a Gold status when added 10,000 points ($100,000)")
    public void memberWith100EarnsGoldAfterSpends$100000() {
        memberSteps.aMemberHasPointsOf(100);
        memberSteps.theMemberSpends(100_000);
        memberSteps.theMemberShouldHaveAStatusOf(Gold);
    }

}
