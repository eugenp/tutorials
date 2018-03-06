JMX Best Practices Source Code, Instructions for Windows.

There are two examples in this source code distribution:

1. Simple Dynamic MBean Facade demonstration
2. Externalization of MBean interface demonstration

SOFTWARE DEPENDENCIES
---------------------
The demo applications contained in this distribution are dependent on the following software:

1. J2SE Java Development Kit (JDK) 1.3 or higher - http://java.sun.com/j2se/

2. Java Management Extensions (JMX) - http://java.sun.com/products/JavaManagement/

3. Ant - http://ant.apache.org/

4. Java Extensions for XML Processing (JAXP) - http://java.sun.com/webservices/webservicespack.html

Download and install these software packages on your machine. Remember the directories where you installed them, because you will need to modify certain portions of the build files (described below).

These demos are contained in jmxbp\ex1 and jmxbp\ex2, respectively. In order to run these demonstration applications, you will most likely need to modify the build scripts.


BUILD.XML
---------
The following properties need to be modified in the Ant script for building the demo applications:

1. source_root - needs to point to the root directory of the location where you      unzipped the distribution. For example:

<property name="source_root" value="c:\jmxbook"/>

presumes the distribution has been unzipped at C:\jmxbook


2. jmx_home - needs to point to the root directory of the JMX implementation you have installed. For example:

<property name="jmx_home" value="c:\jmx-1_2-mr"/>

assumes the JMX libraries have been installed at c:\jmx-1_2-mr

NOTE: the location of the JMX binaries relative to the root changed from JMX RI 1.0 to 1.1. If you are using JMX RI 1.0 you will also need to modify the "path" in the Ant script as well.


3. jaxp_home - the location of the JAXP implementation you're using. For example:

<property name="jaxp_home" value="C:\java_xml_pack-summer-02_01\jaxp-1.2_01"/>

assumes that the JAXP implementation is located at C:\java_xml_pack-summer-02_01\jaxp-1.2_01



BUILD-EXx.BAT
-------------
The following environment variables need to be modified in both build BAT files in order to run both versions of the demo app:

1. JAVA_HOME - the location of the J2SE Java Development Kit you installed. For example:

@set JAVA_HOME=c:\jdk1.3.1_06

will set the JAVA_HOME environment variable to point to c:\jdk1.3.1_06


2. JMX_HOME - needs to point to the root directory of the JMX implementation you have installed. For example:

@set JMX_HOME=c:\jmx-1_2-mr

assumes the JMX libraries have been installed at c:\jmx-1_2-mr

NOTE: the location of the JMX binaries relative to the root changed from JMX RI 1.0 to 1.1. If you are using JMX RI 1.0 you will also need to modify the "path" in the Ant script as well.


3. ANT_HOME - the location of the Ant software you installed. For example:

@set ANT_HOME=c:\jakarta-ant-1.5.1

assumes that Ant is installed at c:\jakarta-ant-1.5.1


4. JAXP_HOME - the location of the JAXP implementation you're using. For example:

@set JAXP_HOME=c:\java_xml_pack-summer-02_01\jaxp-1.2_01

assumes that the JAXP implementation is located at C:\java_xml_pack-summer-02_01\jaxp-1.2_01


CLEAN.BAT
---------
The following environment variables need to be modified in order to run the clean script:

1. JAVA_HOME - the location of the J2SE Java Development Kit you installed. For example:

@set JAVA_HOME=c:\jdk1.3.1_06

will set the JAVA_HOME environment variable to point to c:\jdk1.3.1_06


2. ANT_HOME - the location of the Ant software you installed. For example:

@set ANT_HOME=c:\jakarta-ant-1.5.1

assumes that Ant is installed at c:\jakarta-ant-1.5.1


MONITORING THE DEMO APPLICATIONS
--------------------------------
The BUILD-EX1.BAT and BUILD-EX2.BAT batch files will build and run each version of the demo application. Once the application is running, point your browser to http://machine:8090, where machine is the name of the machine where the demo application is running (most likely localhost).