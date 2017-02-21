import com.baeldung.spring_groovy_config.TestClassB

beans{
    testClassB(TestClassB){
        testStringB = "Test String"
        testIntB = 10
    }
}