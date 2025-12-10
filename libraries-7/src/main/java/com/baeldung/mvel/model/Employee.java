package com.baeldung.mvel.model;

public class Employee {
    private String name;
    private Job job;

    public Employee(String name, Job job) {
        this.name = name;
        this.job = job;
    }

    public Employee(String name, int experienceInYears, double salary, String department) {
        this.name = name;
        this.job = new Job(experienceInYears, salary, department);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }


    public static class Job {
        private int experienceInYears;
        private double salary;
        private String department;

        public Job(int experienceInYears, double salary) {
            this.experienceInYears = experienceInYears;
            this.salary = salary;
        }

        public Job(int experienceInYears, double salary, String department) {
            this.experienceInYears = experienceInYears;
            this.salary = salary;
            this.department = department;
        }

        public int getExperienceInYears() {
            return experienceInYears;
        }

        public void setExperienceInYears(int experienceInYears) {
            this.experienceInYears = experienceInYears;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public double calculateAnnualBonus() {
            if (experienceInYears > 10) {
                return salary * 0.20;
            } else if (experienceInYears > 5) {
                return salary * 0.10;
            } else {
                return salary * 0.05;
            }
        }
    }

}
