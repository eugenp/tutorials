package com.baeldung.mybatisplus.codegenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MyBatisPlusCodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:h2:file:~/mybatisplus", "sa", "")
            .globalConfig(builder -> {
                builder.author("anshulbansal")
                    .outputDir("../tutorials/mybatis-plus/src/main/java/");
            })
            .packageConfig(builder -> builder.parent("com.baeldung.mybatisplus.codegenerator"))
            .templateEngine(new FreemarkerTemplateEngine())
            .execute();

    }

}