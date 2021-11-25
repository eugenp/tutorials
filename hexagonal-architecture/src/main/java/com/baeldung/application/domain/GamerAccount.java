package com.baeldung.application.domain;

public class GamerAccount {

    private Long id;
    private int rankLevel;

    public GamerAccount(Long id, int rankLevel) {
        this.id = id;
        this.rankLevel = rankLevel;
    }

    public GamerAccount() {

    }

    public boolean reduceRankLevel(int level) {

        if(rankLevel>0){
            if(rankLevel-level<0) {
                rankLevel = 0;
            }else {
                rankLevel=-level;
            }
        }
        return true;
    }

    public void addRankLevel(int level) {
        rankLevel = rankLevel+level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(int rankLevel) {
        this.rankLevel = rankLevel;
    }

}
