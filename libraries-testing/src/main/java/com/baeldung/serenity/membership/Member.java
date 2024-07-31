package com.baeldung.serenity.membership;

import static com.baeldung.serenity.membership.MemberGrade.Bronze;
import static com.baeldung.serenity.membership.MemberGrade.Gold;
import static com.baeldung.serenity.membership.MemberGrade.Silver;

/**
 * @author aiet
 */
public class Member {

    private int points;

    private Member(int points) {
        if (points < 0)
            throw new IllegalArgumentException("points must not be negative!");
        this.points = points;

    }

    public static Member withInitialPoints(int initialPoints) {
        return new Member(initialPoints);
    }

    public MemberGrade getGrade() {
        if (points < 1000)
            return Bronze;
        else if (points >= 1000 && points < 5000)
            return Silver;
        else
            return Gold;
    }

    public void spend(int moneySpent) {
        points += moneySpent / 10;
    }

}
