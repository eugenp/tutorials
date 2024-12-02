package com.baeldung.interpreter;

import java.util.List;

interface Expression {
    List<String> interpret(Context ctx);
}