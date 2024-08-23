package com.baeldung.mybatisplus.codegenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MyBatisPlusCodeGenerator {

    public void generateCode() {
        FastAutoGenerator.create("jdbc:h2:file:~/mybatisplus", "sa", "")
            .globalConfig(builder -> {
                builder.author("anshulbansal")
                    .outputDir("../tutorials/mybatis-plus/src/main/java/")
                    .disableOpenDir();
            })
            .packageConfig(builder -> builder.parent("com.baeldung.mybatisplus").service("ClientService"))
            .templateEngine(new FreemarkerTemplateEngine())
            .execute();
    }

}