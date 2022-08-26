package com.baeldung.deepvsshallowcopy;

public class Program {

    private String name;
    private String programMission;
    private String programVision;

    public Program() {
        // TODO Auto-generated constructor stub
    }

    public Program(String name, String programMission, String programVision) {
        super();
        this.name = name;
        this.programMission = programMission;
        this.programVision = programVision;
    }

    public Program(Program program) {
        this(program.getName(), program.getProgramMission(), program.getProgramVision());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgramMission() {
        return programMission;
    }

    public void setProgramMission(String programMission) {
        this.programMission = programMission;
    }

    public String getProgramVision() {
        return programVision;
    }

    public void setProgramVision(String programVision) {
        this.programVision = programVision;
    }

}
