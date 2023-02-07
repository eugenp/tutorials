package com.baeldung.deepshallowcopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentDeepCopyUnitTest {


    @Test
    void whenDeepCopying_thenCopyObjectMakesCopyOfReferencedObjectInSourceObject(){
        SchoolDeepCopy ug = new SchoolDeepCopy("University of Ghana");
        StudentDeepCopy studentOne = new StudentDeepCopy("Abena", "Kojo","200L", ug );
        StudentDeepCopy studentTwo = null;

        try{
            studentTwo = (StudentDeepCopy) studentOne.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
        }

        assertNotSame(studentOne.getSchool(),studentTwo.getSchool());
        studentTwo.getSchool().setName("University of Nigeria");
        assertNotEquals(studentOne.getSchool().getName(), studentTwo.getSchool().getName());

    }

}