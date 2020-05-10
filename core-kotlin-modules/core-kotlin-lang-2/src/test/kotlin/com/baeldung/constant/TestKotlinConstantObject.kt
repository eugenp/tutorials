package com.baeldung.constant


object TestKotlinConstantObject {
    const val COMPILE_TIME_CONST = 10

    val RUN_TIME_CONST: Int

    @JvmField
    val JAVA_STATIC_FINAL_FIELD = 20

    init {
        RUN_TIME_CONST = TestKotlinConstantObject.COMPILE_TIME_CONST + 20;
    }
}