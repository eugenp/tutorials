package com.baeldung.comparator;

import java.util.Comparator;

/**
 * @author zn.wang
 */
public class PlayerAgeComparator implements Comparator<Player> {

    @Override
    public int compare(Player firstPlayer, Player secondPlayer) {
       return (firstPlayer.getAge() - secondPlayer.getAge());
    }

}
