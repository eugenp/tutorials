import com.baeldung.spring_groovy_config.GroovyClass
import com.baeldung.spring_groovy_config.ClassWithRef

beans{
    groovyClass(GroovyClass, groovyInt:5)
    classWithRef(ClassWithRef){
        myClass = groovyClass
    }
    
}