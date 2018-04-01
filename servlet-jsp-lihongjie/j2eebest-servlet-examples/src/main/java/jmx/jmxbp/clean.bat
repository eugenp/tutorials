@echo off
@rem
@rem Cleans up all .class files
@rem
@rem Author: Steve Perry

@set TARGET_NAME=clean

@set JAVA_HOME=c:\jdk1.3.1_06

@rem
@rem Set the ANT_HOME environment variable...
@rem
@set ANT_HOME=c:\jakarta-ant-1.5.1

@echo Starting Build ...

call %ANT_HOME%\bin\ant %TARGET_NAME%

:DONE
