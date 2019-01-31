package com.baeldung.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONS")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String gender;
    private String homeRun;
    private String runningClub;
    private String totalRuns = "0";
    private String email;
    private String postCode;

    public Person() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the homeRun
     */
    public String getHomeRun() {
        return homeRun;
    }

    /**
     * @param homeRun the homeRun to set
     */
    public void setHomeRun(String homeRun) {
        this.homeRun = homeRun;
    }

    /**
     * @return the runningClub
     */
    public String getRunningClub() {
        return runningClub;
    }

    /**
     * @param runningClub the runningClub to set
     */
    public void setRunningClub(String runningClub) {
        this.runningClub = runningClub;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the postCode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode the postCode to set
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @return the totalRuns
     */
    public String getTotalRuns() {
        return totalRuns;
    }

    /**
     * @param totalRuns the totalRuns to set
     */
    public void setTotalRuns(String totalRuns) {
        this.totalRuns = totalRuns;
    }

}
