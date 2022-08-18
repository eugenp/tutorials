package com.baeldung.optaplanner;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@PlanningSolution
public class CourseSchedule {

    Logger logger = LoggerFactory.getLogger("CourseSchedule");
    @ValueRangeProvider(id = "availableRooms")
    @ProblemFactCollectionProperty
    private List<Integer> roomList;
    @ValueRangeProvider(id = "availablePeriods")
    @ProblemFactCollectionProperty
    private List<Integer> periodList;
    @PlanningEntityCollectionProperty
    private List<Lecture> lectureList;
    @PlanningScore
    private HardSoftScore score;


    public CourseSchedule(){
        roomList = new ArrayList<>();
        periodList = new ArrayList<>();
        lectureList = new ArrayList<>();
    }

    public List<Integer> getRoomList() {
        return roomList;
    }

    public List<Integer> getPeriodList() {
        return periodList;
    }

    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public void setRoomList(List<Integer> roomList) {
        this.roomList = roomList;
    }

    public void setPeriodList(List<Integer> periodList) {
        this.periodList = periodList;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

    public void printCourseSchedule() {
        lectureList.stream()
                .map(c -> "Lecture in Room " + c.getRoomNumber().toString() + " during Period " + c.getPeriod().toString())
                .forEach(k -> logger.info(k));
    }

}
