package com.baeldung.drools.optaplanner;

import java.util.HashSet;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class ScoreCalculator implements EasyScoreCalculator<CourseSchedule> {

    @Override
    public Score calculateScore(CourseSchedule courseSchedule) {
        int hardScore = 0;
        int softScore = 0;

        HashSet<String> occupiedRooms = new HashSet<>();
        for (Lecture lecture : courseSchedule.getLectureList()) {
            if(lecture.getPeriod() != null && lecture.getRoomNumber() != null) {
                String roomInUse = lecture.getPeriod().toString() + ":" + lecture.getRoomNumber().toString();
                if (occupiedRooms.contains(roomInUse)) {
                    hardScore += -1;
                } else {
                    occupiedRooms.add(roomInUse);
                }
            } else {
                hardScore += -1;
            }
        }

        return HardSoftScore.valueOf(hardScore, softScore);
    }
}
