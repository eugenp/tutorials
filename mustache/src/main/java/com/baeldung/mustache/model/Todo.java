package com.baeldung.mustache.model;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.function.Function;

public class Todo {

    public Todo(){}
    
    public Todo(String title, String text){
        this.title = title;
        this.text = text;
        createdOn = new Date();
    }
    
    private String title;
    private String text;
    private boolean done = false;
    
    private Date createdOn;
    private Date completedOn;
    
    public void markAsDone(){
        done = true;
        completedOn = new Date();
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getDone() {
        return done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }
    
    public long doneSince(){
        if ( done ){
            return Duration.between(createdOn.toInstant(), completedOn.toInstant()).toMinutes();
        }
        return 0;
    }
    
    public Function<Object, Object> handleDone(){
        return (obj) -> {       
            if ( done ){
                return String.format("<small>Done %s minutes ago<small>", obj);
            }else{
                return "";
            }
            
        };
        
    }
    
}
