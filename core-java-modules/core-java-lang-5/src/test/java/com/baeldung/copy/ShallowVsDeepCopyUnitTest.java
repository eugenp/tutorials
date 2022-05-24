package com.baeldung.copy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShallowVsDeepCopyUnitTest {

    @Test
    void whenShallowCopyVersionIsModified_thenOriginalVersionIsAlsoChanged() {
        Lesson originalLesson = new Lesson("CSE-01", 10);
        ShallowCopyStudent originalStudent = new ShallowCopyStudent("Doga", 1, originalLesson);

        ShallowCopyStudent copiedStudent = (ShallowCopyStudent) originalStudent.clone();
        copiedStudent.setName("Zeynep");
        copiedStudent.setGrade(3);
        copiedStudent.getLesson()
            .setName("CSE-07");
        copiedStudent.getLesson()
            .setCredit(5);

        Assertions.assertEquals("CSE-07", originalStudent.getLesson()
            .getName());
        Assertions.assertEquals(5, originalStudent.getLesson()
            .getCredit());
        //Original reference attribute (Lesson) is also changed, when copied version is set
        Assertions.assertEquals(copiedStudent.getLesson()
            .getName(), originalStudent.getLesson()
            .getName());
        Assertions.assertEquals(copiedStudent.getLesson()
            .getCredit(), originalStudent.getLesson()
            .getCredit());
    }

    @Test
    void whenDeepCopyVersionIsModified_thenOriginalVersionIsNotChanged() {
        Lesson originalLesson = new Lesson("CSE-01", 10);
        DeepCopyStudent originalStudent = new DeepCopyStudent("Doga", 1, originalLesson);

        DeepCopyStudent copiedStudent = (DeepCopyStudent) originalStudent.clone();
        copiedStudent.setName("Zeynep");
        copiedStudent.setGrade(3);
        copiedStudent.getLesson()
            .setName("CSE-07");
        copiedStudent.getLesson()
            .setCredit(5);

        Assertions.assertEquals("CSE-01", originalStudent.getLesson()
            .getName());
        Assertions.assertEquals(10, originalStudent.getLesson()
            .getCredit());
        //Original reference attribute (Lesson) is NOT changed, when copied version is set
        Assertions.assertNotEquals(copiedStudent.getLesson()
            .getName(), originalStudent.getLesson()
            .getName());
        Assertions.assertNotEquals(copiedStudent.getLesson()
            .getCredit(), originalStudent.getLesson()
            .getCredit());
    }

    private class Lesson implements Cloneable {
        private String name;
        private int credit;

        public Lesson(String name, int credit) {
            setName(name);
            setCredit(credit);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        @Override
        public Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
    }

    private class ShallowCopyStudent implements Cloneable {
        private String name;
        private int grade;
        private Lesson lesson;

        public ShallowCopyStudent(String name, int grade, Lesson lesson) {
            setName(name);
            setGrade(grade);
            setLesson(lesson);
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Lesson getLesson() {
            return lesson;
        }

        public void setLesson(Lesson lesson) {
            this.lesson = lesson;
        }

        @Override
        public Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
    }

    private class DeepCopyStudent implements Cloneable {
        private String name;
        private int grade;
        private Lesson lesson;

        public DeepCopyStudent(String name, int grade, Lesson lesson) {
            setName(name);
            setGrade(grade);
            setLesson(lesson);
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Lesson getLesson() {
            return lesson;
        }

        public void setLesson(Lesson lesson) {
            this.lesson = lesson;
        }

        @Override
        public Object clone() {
            Lesson copiedLesson = (Lesson) this.lesson.clone();
            try {
                DeepCopyStudent copiedStudent = (DeepCopyStudent) super.clone();
                //There's no need to copy of the rest attributes, since they are primitive (int) and immutable (String)
                copiedStudent.setLesson(copiedLesson);
                return copiedStudent;
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
    }
}
