package com.baeldung.pattern.hexagonal.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Table(name = "employee") public class Employee implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = -5605175902240159269L;

        private Integer id;

        private String name;

        private Integer age;

        private String department;

        public Employee() {
        }

        public Employee(Integer id, String name, Integer age, String department) {
                super();
                this.id = id;
                this.name = name;
                this.age = age;
                this.department = department;
        }

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        @Column(name = "name") public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        @Column(name = "age") public Integer getAge() {
                return age;
        }

        public void setAge(Integer age) {
                this.age = age;
        }

        @Column(name = "department") public String getDepartment() {
                return department;
        }

        public void setDepartment(String department) {
                this.department = department;
        }

        @Override public String toString() {
                return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", department=" + department + "]";
        }

}
