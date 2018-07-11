package com.baeldung.sqlresultsetmapping;

import javax.persistence.*;

@SqlResultSetMappings(value = {
    @SqlResultSetMapping(name = "ScheduleResult",
        classes = { @ConstructorResult(targetClass = com.baeldung.sqlresultsetmapping.ScheduledDay.class,
        columns = { @ColumnResult(name = "id", type = Long.class),
                    @ColumnResult(name = "employeeId", type = Long.class),
                    @ColumnResult(name = "hourIn"),
                    @ColumnResult(name = "hourOut"),
                    @ColumnResult(name = "dayOfWeek") }) }),
    @SqlResultSetMapping(name = "FridayEmployeeResult",
                         columns = { @ColumnResult(name = "employeeId") }),
    @SqlResultSetMapping(name = "EmployeeScheduleResults",
                         entities = { @EntityResult(entityClass = com.baeldung.sqlresultsetmapping.Employee.class),
                                      @EntityResult(entityClass = com.baeldung.sqlresultsetmapping.ScheduledDay.class)
    }) })
@NamedNativeQuery(name = "FridayEmployees",
        query = "SELECT employeeId FROM schedule_days WHERE dayOfWeek = 'FRIDAY'",
        resultSetMapping = "FridayEmployeeResult")

@NamedNativeQuery(name = "Schedules",
        query = "SELECT * FROM schedule_days WHERE hourIn = 8",
        resultSetMapping = "ScheduleResult")
@Entity
@Table(name = "SCHEDULE_DAYS")
public class ScheduledDay {

    @Id
    @GeneratedValue
    private Long id;
    private Long employeeId;
    private Integer hourIn;
    private Integer hourOut;
    private String dayOfWeek;

    public ScheduledDay() {
    }

    public ScheduledDay(Long id, Long employeeId, Integer hourIn, Integer hourOut, String dayofWeek) {
        this.id = id;
        this.employeeId = employeeId;
        this.hourIn = hourIn;
        this.hourOut = hourOut;
        this.dayOfWeek = dayofWeek;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getHourIn() {
        return hourIn;
    }

    public void setHourIn(Integer hourIn) {
        this.hourIn = hourIn;
    }

    public Integer getHourOut() {
        return hourOut;
    }

    public void setHourOut(Integer hourOut) {
        this.hourOut = hourOut;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
