package com.baeldung.cxf.introduction;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(StudentAdapter.class)
public interface Student {
    public String getName();
}