package com.baeldung.hellopant;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import java.io.IOException;
import java.util.List;

public class Hello {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        final List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        final String result = Joiner.on(",").skipNulls().join(names);

        System.out.println(result);

  }
}
