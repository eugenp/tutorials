package org.disco.racer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

public class Result {
    private ResultId resultId;
    private BigDecimal time;
    private int ranking;

    private Runner runner;
    private Race race;

    public Result() {
    }

    public Race getRace() {
        return race;
    }

    public void setRace(final Race race) {
        this.race = race;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(final Runner runner) {
        this.runner = runner;
    }

    public ResultId getResultId() {
        return resultId;
    }

    public void setResultId(final ResultId resultID) {
        this.resultId = resultID;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(final BigDecimal time) {
        this.time = time;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(final int ranking) {
        this.ranking = ranking;
    }


    public boolean equals(final Object obj) {
        if (!(obj instanceof Result)) {
            return false;
        }
        Result rhs = (Result) obj;
        return new EqualsBuilder().

                append(this.time, rhs.time).
                append(this.ranking, rhs.ranking).
                isEquals();
    }

    /**
     *
     */
    public int hashCode() {
        return new HashCodeBuilder(5, 17).
                append(this.time).
                append(this.ranking).
                toHashCode();
    }

    public static class ResultId implements Serializable {
        private Long runnerId;
        private Long raceId;

        public ResultId() {
        }

        public ResultId(final Long runnerId, final Long raceId) {
            this.runnerId = runnerId;
            this.raceId = raceId;
        }

        public long getRunnerId() {
            return runnerId;
        }

        public void setRunnerId(final Long runnerId) {
            this.runnerId = runnerId;
        }

        public long getRaceId() {
            return raceId;
        }

        public void setRaceId(final Long raceId) {
            this.raceId = raceId;
        }

        public boolean equals(final Object obj) {
            if (!(obj instanceof ResultId)) {
                return false;
            }
            ResultId rhs = (ResultId) obj;
            return new EqualsBuilder().

                    append(this.raceId, rhs.raceId).
                    append(this.runnerId, rhs.runnerId).
                    isEquals();
        }


        public int hashCode() {
            return new HashCodeBuilder(9, 13).
                    append(this.raceId).
                    append(this.runnerId).
                    toHashCode();
        }

    }
}
