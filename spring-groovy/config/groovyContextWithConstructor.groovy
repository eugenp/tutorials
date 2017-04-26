import com.baeldung.spring_groovy_config.TestClass

beans{
    testString String, 'Test String'
    testClass(TestClass){
        beanDefinition ->
            beanDefinition.constructorArgs = ["Test String",10.2]
    }
}