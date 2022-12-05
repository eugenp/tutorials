package com.baeldung.shallowDeepCopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentTest {

        @Test
        void whenShallowCopying_thenCopyObjectAndSourceObjectShareSameReferencedObject(){
            SchoolShallowCopy ug = new SchoolShallowCopy("University of Ghana");
            StudentShallowCopy studentOne = new StudentShallowCopy("Abena", "Kojo","200L", ug );
            StudentShallowCopy studentTwo = null;
            try{
                studentTwo = (StudentShallowCopy) studentOne.clone();
            } catch (CloneNotSupportedException e){
                e.printStackTrace();
            }
            assertSame(studentOne.getSchool(),studentTwo.getSchool());
            studentTwo.getSchool().setName("University of Nigeria");
            assertEquals(studentOne.getSchool().getName(), studentTwo.getSchool().getName());
        }

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