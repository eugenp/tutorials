@echo off

@set PACKAGE_NAME=ex2
@set TARGET_NAME=build-jmxbp-%PACKAGE_NAME%

@rem ===========================================
@rem = You will need to modify these according =
@rem = to where your JDK, JMX RI and Ant are   =
@rem = installed.                              =
@rem ===========================================
@set JAVA_HOME=c:\jdk1.3.1_06
@set JMX_HOME=c:\jmx-1_2-mr
@set ANT_HOME=c:\jakarta-ant-1.5.1
@set JAXP_HOME=c:\java_xml_pack-summer-02_01\jaxp-1.2_01

@set JMX_LIB_HOME=%JMX_HOME%\lib
@set CP=%CLASSPATH%;%JMX_LIB_HOME%\jmxri.jar;%JMX_LIB_HOME%\jmxtools.jar;%JAXP_HOME%\dom.jar;%JAXP_HOME%\jaxp-api.jar;%JAXP_HOME%\sax.jar;%JAXP_HOME%\xercesImpl.jar;%JAXP_HOME%\xalan.jar;.

@echo Starting Build ...
call %ANT_HOME%\bin\ant %TARGET_NAME%

@rem This 'if' does not work on Windows ME
@rem Uncomment if you're running on Windows 2000/NT
if NOT "%ERRORLEVEL%"=="0" goto DONE

%JAVA_HOME%\bin\java -classpath %CP% jmxbp.%PACKAGE_NAME%.Controller myapp.xml

:DONE
