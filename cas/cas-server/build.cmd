@echo off

@set JAVA_ARGS=-Xms500m -Xmx1g
@set CAS_DIR=\etc\cas
@set CONFIG_DIR=\etc\cas\config

@rem Call this script with DNAME and CERT_SUBJ_ALT_NAMES already set to override
@if "%DNAME%" == "" set DNAME=CN=cas.example.org,OU=Example,OU=Org,C=US
@rem List other host names or ip addresses you want in your certificate, may help with host name verification, 
@rem   if client apps make https connection for ticket validation and compare name in cert (include sub. alt. names) 
@rem   to name used to access CAS
@if "%CERT_SUBJ_ALT_NAMES%" == "" set CERT_SUBJ_ALT_NAMES=dns:example.org,dns:localhost,dns:%COMPUTERNAME%,ip:127.0.0.1

@rem Check for mvn in path, use it if found, otherwise use maven wrapper
@set MAVEN_CMD=mvn
@where /q mvn
@if %ERRORLEVEL% neq 0 set MAVEN_CMD=.\mvnw.bat

@if "%1" == "" call:help
@if "%1" == "copy" call:copy
@if "%1" == "clean" call:clean %2 %3 %4
@if "%1" == "package" call:package %2 %3 %4
@if "%1" == "bootrun"  call:bootrun %2 %3 %4
@if "%1" == "debug" call:debug %2 %3 %4
@if "%1" == "run" call:run %2 %3 %4
@if "%1" == "runalone" call:runalone %2 %3 %4
@if "%1" == "help" call:help
@if "%1" == "gencert" call:gencert
@if "%1" == "cli" call:runcli %2 %3 %4

@rem function section starts here
@goto:eof

:copy
    @echo "Creating configuration directory under %CONFIG_DIR%"
    if not exist %CONFIG_DIR% mkdir %CONFIG_DIR%

    @echo "Copying configuration files from etc/cas to /etc/cas"
    xcopy /S /Y etc\cas\* \etc\cas
@goto:eof

:help
    @echo "Usage: build.bat [copy|clean|package|run|debug|bootrun|gencert|cli] [optional extra args for maven or cli]"
    @echo "To get started on a clean system, run "build.bat copy" and "build.bat gencert", then "build.bat run"
    @echo "Note that using the copy or gencert arguments will create and/or overwrite the %CAS_DIR% which is outside this project"
@goto:eof

:clean
    call %MAVEN_CMD% clean %1 %2 %3
    exit /B %ERRORLEVEL%
@goto:eof

:package
    call %MAVEN_CMD% clean package -T 5 %1 %2 %3
    exit /B %ERRORLEVEL%
@goto:eof

:bootrun
    call %MAVEN_CMD% clean package spring-boot:run -T 5 %1 %2 %3
    exit /B %ERRORLEVEL%
@goto:eof

:debug
    call:package %1 %2 %3 & java %JAVA_ARGS% -Xdebug -Xrunjdwp:transport=dt_socket,address=5000,server=y,suspend=n -jar target/cas.war
@goto:eof

:run
    call:package %1 %2 %3 & java %JAVA_ARGS% -jar target/cas.war
@goto:eof

:runalone
    call:package %1 %2 %3 & target/cas.war
@goto:eof

:gencert
    where /q keytool
    if ERRORLEVEL 1 (
        @echo Java keytool.exe not found in path. 
        exit /b 1
    ) else (
        if not exist %CAS_DIR% mkdir %CAS_DIR%
        @echo on
        @echo Generating self-signed SSL cert for %DNAME% in %CAS_DIR%\thekeystore
        keytool -genkeypair -alias cas -keyalg RSA -keypass changeit -storepass changeit -keystore %CAS_DIR%\thekeystore -dname %DNAME% -ext SAN=%CERT_SUBJ_ALT_NAMES%
        @echo Exporting cert for use in trust store (used by cas clients)
        keytool -exportcert -alias cas -storepass changeit -keystore %CAS_DIR%\thekeystore -file %CAS_DIR%\cas.cer
    )
@goto:eof

:runcli
    for /f %%i in ('call %MAVEN_CMD% -q --non-recursive "-Dexec.executable=cmd" "-Dexec.args=/C echo ${cas.version}" "org.codehaus.mojo:exec-maven-plugin:1.3.1:exec"') do set CAS_VERSION=%%i
    @set CAS_VERSION=%CAS_VERSION: =%
    @set DOWNLOAD_DIR=target
    @set COMMAND_FILE=cas-server-support-shell-%CAS_VERSION%.jar
    @if not exist %DOWNLOAD_DIR% mkdir %DOWNLOAD_DIR%
    @if not exist %DOWNLOAD_DIR%\%COMMAND_FILE% ( 
        @call mvn org.apache.maven.plugins:maven-dependency-plugin:3.0.2:get -DgroupId=org.apereo.cas -DartifactId=cas-server-support-shell -Dversion=%CAS_VERSION% -Dpackaging=jar -DartifactItem.outputDirectory=%DOWNLOAD_DIR% -DartifactItem.destFileName=%COMMAND_FILE% -DremoteRepositories=central::default::http://repo1.maven.apache.org/maven2,snapshots::::https://oss.sonatype.org/content/repositories/snapshots -Dtransitive=false
        @call mvn org.apache.maven.plugins:maven-dependency-plugin:3.0.2:copy -Dmdep.useBaseVersion=true -Dartifact=org.apereo.cas:cas-server-support-shell:%CAS_VERSION%:jar -DoutputDirectory=%DOWNLOAD_DIR%
    )
    @call java %JAVA_ARGS% -jar %DOWNLOAD_DIR%\%COMMAND_FILE% %1 %2 %3

@goto:eof