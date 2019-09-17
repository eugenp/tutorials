package com.baeldung.architecture;

import java.io.Serializable;

public class MonthlyStats implements Serializable {
    private static final long serialVersionUID = 1L;
    private int goals;
    private int assists;
    private int passes;
    private int tackles;
    private double rating;

    MonthlyStats(int goals, int assists, int passes, int tackles, double rating) {
        this.goals = goals;
        this.assists = assists;
        this.passes = passes;
        this.tackles = tackles;
        this.rating = rating;
    }
}
