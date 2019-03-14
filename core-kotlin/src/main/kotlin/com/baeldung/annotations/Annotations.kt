package com.baeldung

@Target(AnnotationTarget.FIELD)
annotation class Positive

@Target(AnnotationTarget.FIELD)
annotation class AllowedNames(val names: Array<String>)
