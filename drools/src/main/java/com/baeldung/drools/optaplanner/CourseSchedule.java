package com.baeldung.drools.optaplanner;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PlanningSolution
public class CourseSchedule {

    Logger logger = LoggerFactory.getLogger("CourseSchedule");

    private List<Integer> roomList;
    private List<Integer> periodList;
    private List<Lecture> lectureList;
    private HardSoftScore score;

    public CourseSchedule(){
        roomList = new ArrayList<>();
        periodList = new ArrayList<>();
        lectureList = new ArrayList<>();
    }

    @ValueRangeProvider(id = "availableRooms")
    @ProblemFactCollectionProperty
    public List<Integer> getRoomList() {
        return roomList;
    }

    @ValueRangeProvider(id = "availablePeriods")
    @ProblemFactCollectionProperty
    public List<Integer> getPeriodList() {
        return periodList;
    }

    @PlanningEntityCollectionProperty
    public List<Lecture> getLectureList() {
        return lectureList;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public void printCourseSchedule() {
        lectureList.stream()
                .map(c -> "Lecture in Room " + c.getRoomNumber().toString() + " during Period " + c.getPeriod().toString())
                .forEach(k -> logger.info(k));
    }

}
