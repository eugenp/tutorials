package com.baeldung.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class Subject implements Serializable, Cloneable {
private String subjectName;
private String subjectId;

public Subject(Subject subject) {
this(subject.getSubjectName(), subject.getSubjectId());
}

@Override
public Object clone() {
try {
return (Subject) super.clone();
} catch (CloneNotSupportedException e) {
return new Subject(this.subjectName, this.subjectId);
}
}
}