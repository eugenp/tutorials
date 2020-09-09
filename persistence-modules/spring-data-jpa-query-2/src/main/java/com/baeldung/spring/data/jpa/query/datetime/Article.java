package com.baeldung.spring.data.jpa.query.datetime;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Article {

    @Id
    @GeneratedValue
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date publicationDate;
    @Temporal(TemporalType.TIME)
    private Date publicationTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;

    public Integer getId() {
        return id;
    }

}
