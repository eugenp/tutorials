package com.baeldung.optaplanner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

import java.util.HashSet;

public class ScoreCalculator implements EasyScoreCalculator<CourseSchedule, HardSoftScore> {

    @Override
    public HardSoftScore calculateScore(CourseSchedule courseSchedule) {
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

        return HardSoftScore.of(hardScore, softScore);
    }
}
