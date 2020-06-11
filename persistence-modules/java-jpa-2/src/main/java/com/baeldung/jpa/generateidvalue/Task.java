package com.baeldung.jpa.generateidvalue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @TableGenerator(name = "id_generator", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_value", pkColumnValue = "task_gen", initialValue = 10000, allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}