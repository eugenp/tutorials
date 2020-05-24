package com.baeldung.architecture.hexagonal.component;

import java.util.Date;

public interface StringTransformationLogger {
    void logTransformation(Date date, String original, String transformed);
}
