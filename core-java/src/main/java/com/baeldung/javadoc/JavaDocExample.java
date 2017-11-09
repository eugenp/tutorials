package com.baeldung.javadoc;

import java.awt.List;
import java.io.IOException;

/**
 * @author Baeldung
 * @version 1.0
 * <h1> Description of the Class </h1>
 * <a href="http://www.baeldung.com/"> Baeldung </a>
 * @see "Oracle Javadoc"
 * @see <a href="http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#see" >javadoc Tool</a>
 * @see com.baeldung.javadoc.JavaDocExample#JavaDocExample() default constructor
 * @since 1.5
 * this class is deprecated and you can use {@link String}
 *
 */

public class JavaDocExample {

    /**
     * @param name descripton of parameter <br>
     * <pre>
     * {@code 
     *  public void setName(String name){
     *     content here
     *  }
     * }
     * </pre>
     * 
     */
    public void setName(String name){

    }

    /**
     * @return description of the returned Value
     * {@link java.lang.String}
     */
    public String getName(){
        return "";
    }

    /**
     * @throws IOException description
     */
    public void methodToDoSomething() throws IOException {

    }

}
