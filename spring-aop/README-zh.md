# Spring AOP

本模块包含有关 Spring 面向方面编程 (AOP) 的文章及示例代码。

## 相关文章

- [Spring AOP](SpringAOP.md)
- [AspectJ](AspectJ.md)
- More articles: [[next -->]](/spring-aop-2)

## 问题

**DEBUG** 23:48:30.242 [main] DEBUG o.c.groovy.vmplugin.VMPluginFactory - Trying to create VM plugin `org.codehaus.groovy.vmplugin.v9.Java9` by checking `java.lang.Module`, but failed: java.lang.ClassNotFoundException: java.lang.Module.

- **原因**
  spring-boot-dependencies version 2.7.2
  Managed Dependencies org.codehaus.groovy » groovy 3.0.11
  groovy 3.0.11 需要 jdk 9 才能正常运行。
- **处理**
  pom 中指定 groovy 2.5.18
  