package com.baeldung.studentmanager;

import baeldung.StudentOuterClass.Student;
import java.util.Map;

public class Studentmanager {

    public Student createStudent(int id, String name, Map<String, Integer> grades) {
        Student.Builder studentBuilder = Student.newBuilder();
        studentBuilder.setId(id);
        studentBuilder.setName(name);
        studentBuilder.putAllGrades(grades);
        return studentBuilder.build();
    }
    public MultiTypeMap createMultiTypeMap(Map<Integer, String> intToStringMap,
                                           Map<String, Double> stringToDoubleMap,
                                           Map<Boolean, byte[]> boolToBytesMap) {
        MultiTypeMap.Builder multiTypeMapBuilder = MultiTypeMap.newBuilder();
        multiTypeMapBuilder.putIntToString(intToStringMap);
        multiTypeMapBuilder.putStringToDouble(stringToDoubleMap);
        multiTypeMapBuilder.putBoolToBytes(boolToBytesMap);
        return multiTypeMapBuilder.build();
    }
    public byte[] serializeStudent(Student student) {
        return student.toByteArray();
    }

    public Student deserializeStudent(byte[] byteArray) {
        try {
            return Student.parseFrom(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}