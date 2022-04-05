package com.baeldung.jpa.sqlresultsetmapping;

import javax.persistence.*;

@SqlResultSetMappings(value = {
    @SqlResultSetMapping(name = "ScheduleResult",
        classes = { @ConstructorResult(targetClass = com.baeldung.jpa.sqlresultsetmapping.ScheduledDay.class,
        columns = { @ColumnResult(name = "id", type = Long.class),
                    @ColumnResult(name = "employeeId", type = Long.class),
                    @ColumnResult(name = "dayOfWeek") }) }),
    @SqlResultSetMapping(name = "FridayEmployeeResult",
                         columns = { @ColumnResult(name = "employeeId") }),
    @SqlResultSetMapping(name = "EmployeeScheduleResults",
                         entities = { @EntityResult(entityClass = com.baeldung.jpa.sqlresultsetmapping.Employee.class),
                                      @EntityResult(entityClass = com.baeldung.jpa.sqlresultsetmapping.ScheduledDay.class)
    }) })
@NamedNativeQuery(name = "FridayEmployees",
        query = "SELECT employeeId FROM schedule_days WHERE dayOfWeek = 'FRIDAY'",
        resultSetMapping = "FridayEmployeeResult")

@NamedNativeQuery(name = "Schedules",
        query = "SELECT * FROM schedule_days WHERE employeeId = 3",
        resultSetMapping = "ScheduleResult")
@Entity
@Table(name = "SCHEDULE_DAYS")
public class ScheduledDay {

    @Id
    @GeneratedValue
    private Long id;
    private Long employeeId;
    private String dayOfWeek;

    public ScheduledDay() {
    }

    public ScheduledDay(Long id, Long employeeId, String dayofWeek) {
        this.id = id;
        this.employeeId = employeeId;
        this.dayOfWeek = dayofWeek;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
