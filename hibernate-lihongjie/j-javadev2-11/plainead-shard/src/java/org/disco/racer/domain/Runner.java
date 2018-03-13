package org.disco.racer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.math.BigInteger;

public class Runner {
    private BigInteger id;
    private String firstName;
    private String lastName;
    private int age;
    private Set<Race> races;
    private Set<Result> raceResults;
    private static RunnerDAO dao;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("shardedspring-config.xml");
        dao = (RunnerDAO) context.getBean("runner_dao");
    }

    public Runner(final String firstName, final String lastName, final int age, Race race) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.races.add(race);
    }

    public Runner() {
        this.races = new HashSet<Race>();
        this.raceResults = new HashSet<Result>();
    }

    public static Runner findById(final Long id) {
        return dao.findById(id);
    }

    public static Collection<Runner> findByFirstAndLastName(final String fname, final String lname) {
        return dao.findByFirstAndLastName(fname, lname);
    }

    public void create() {
        dao.create(this);
    }

    public void delete() {
        dao.remove(this);
    }

    public void remove() {
        this.delete();
    }

    public void update() {
        dao.update(this);
    }

    public Set<Result> getRaceResults() {
        return raceResults;
    }

    public void addRaceResult(final Result result) {
        this.raceResults.add(result);
    }

    public void setRaceResults(final Set<Result> raceResults) {
        this.raceResults = raceResults;
    }

    public Set<Race> getRaces() {
        return races;
    }

    public void setRaces(final Set<Race> races) {
        this.races = races;
    }

    public void addRace(final Race race) {
        this.races.add(race);
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(final BigInteger id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public boolean equals(final Object obj) {
        if (!(obj instanceof Runner)) {
            return false;
        }
        Runner rhs = (Runner) obj;
        return new EqualsBuilder().
                append(this.id, rhs.id).
                append(this.firstName, rhs.firstName).
                append(this.lastName, rhs.lastName).
                append(this.age, rhs.age).
                isEquals();
    }
    /**
     *
     */
    public int hashCode() {
        return new HashCodeBuilder(9, 77).
                append(this.id).
                append(this.age).
                append(this.firstName).
                append(this.lastName).
                toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).
                append(this.id).
                append(this.age).
                append(this.firstName).
                append(this.lastName).
                toString();
    }
}
