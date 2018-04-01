package org.disco.racer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import java.math.BigInteger;

public class Race {

    private BigInteger id;
    private String name;
    private Date date;
    private double distance;
    private Set<Runner> participants;
    private Set<Result> results;
    private String description;

    private final static RaceDAO dao;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("shardedspring-config.xml");
        dao = (RaceDAO) context.getBean("race_dao");
    }

    public Race() {
        this.participants = new HashSet<Runner>();
        this.results = new HashSet<Result>();
    }

    public Race(final String name, final Date date, final double distance,
                final String description) {
        this.name = name;
        this.date = date;
        this.distance = distance;
        this.description = description;
    }

    public static Collection<Race> findAll(){
        return dao.findAll();
    }

    public static Race findById(Long id) {
        return dao.findById(id);
    }

    public static Race findByName(String name) {
        return dao.findByName(name);
    }

    public void create() {
        dao.create(this);
    }

    public void remove() {
        dao.remove(this);
    }

    public void delete() {
        dao.remove(this);
    }

    public void update() {
        dao.update(this);
    }

    public Set<Result> getResults() {
        return results;
    }

    public void addResult(final Result result) {
        this.results.add(result);
    }

    public void setResults(final Set<Result> raceResults) {
        this.results = raceResults;
    }

    public Set getParticipants() {
        return participants;
    }

    public void setParticipants(final Set<Runner> participants) {
        this.participants = participants;
    }

    public void addParticipant(final Runner participant) {
        this.participants.add(participant);
    }

    public BigInteger getId() {
        return id;
    }

    

    public void setId(final BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(final double distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean equals(final Object obj) {
        if (!(obj instanceof Race)) {
            return false;
        }
        Race rhs = (Race) obj;
        return new EqualsBuilder().
                append(this.id, rhs.id).
                append(this.name, rhs.name).
                append(this.date, rhs.date).
                append(this.distance, rhs.distance).
                isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(7, 47).
                append(this.id).
                append(this.date).
                append(this.name).
                append(this.distance).
                toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).
                append(this.id).
                append(this.date).
                append(this.name).
                append(this.distance).
                toString();
    }
}
