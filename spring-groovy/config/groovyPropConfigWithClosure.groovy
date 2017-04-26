import com.baeldung.spring_groovy_config.TestClassB
import org.springframework.core.io.ClassPathResource

def properties = new Properties()
properties.load(this.getClass().getResourceAsStream("groovy.properties"))

beans{
    testClassB(TestClassB){
        if(properties.testType == 'Default'){
            testStringB = "Default Test String"
            testIntB = 10
        }else{
            testStringB = "Non-Default Test String"
            testIntB = 20
        }
    }
}