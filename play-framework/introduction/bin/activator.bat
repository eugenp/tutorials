@REM activator launcher script
@REM
@REM Environment:
@REM In order for Activator to work you must have Java available on the classpath
@REM JAVA_HOME - location of a JDK home dir (optional if java on path)
@REM CFG_OPTS  - JVM options (optional)
@REM Configuration:
@REM activatorconfig.txt found in the ACTIVATOR_HOME or ACTIVATOR_HOME/ACTIVATOR_VERSION
@setlocal enabledelayedexpansion

@echo off

set "var1=%~1"
if defined var1 (
  if "%var1%"=="help" (
    echo.
    echo Usage activator [options] [command]
    echo.
    echo Commands:
    echo ui                 Start the Activator UI
    echo new [name] [template-id]  Create a new project with [name] using template [template-id]
    echo list-templates     Print all available template names
    echo help               Print this message
    echo.
    echo Options:
    echo -jvm-debug [port]  Turn on JVM debugging, open at the given port.  Defaults to 9999 if no port given.
    echo.
    echo Environment variables ^(read from context^):
    echo JAVA_OPTS          Environment variable, if unset uses ""
    echo SBT_OPTS           Environment variable, if unset uses ""
    echo ACTIVATOR_OPTS     Environment variable, if unset uses ""
    echo.
    echo Please note that in order for Activator to work you must have Java available on the classpath
    echo.
    goto :end
  )
)

@REM determine ACTIVATOR_HOME environment variable
set BIN_DIRECTORY=%~dp0
set BIN_DIRECTORY=%BIN_DIRECTORY:~0,-1%
for %%d in (%BIN_DIRECTORY%) do set ACTIVATOR_HOME=%%~dpd
set ACTIVATOR_HOME=%ACTIVATOR_HOME:~0,-1%

echo ACTIVATOR_HOME=%ACTIVATOR_HOME%

set ERROR_CODE=0
set APP_VERSION=1.3.10
set ACTIVATOR_LAUNCH_JAR=activator-launch-%APP_VERSION%.jar

rem Detect if we were double clicked, although theoretically A user could
rem manually run cmd /c
for %%x in (%cmdcmdline%) do if %%~x==/c set DOUBLECLICKED=1

set SBT_HOME=%BIN_DIRECTORY

rem Detect if we were double clicked, although theoretically A user could
rem manually run cmd /c
for %%x in (%cmdcmdline%) do if %%~x==/c set DOUBLECLICKED=1

rem FIRST we load the config file of extra options.
set FN=%SBT_HOME%\..\conf\sbtconfig.txt
set CFG_OPTS=
FOR /F "tokens=* eol=# usebackq delims=" %%i IN ("%FN%") DO (
  set DO_NOT_REUSE_ME=%%i
  rem ZOMG (Part #2) WE use !! here to delay the expansion of
  rem CFG_OPTS, otherwise it remains "" for this loop.
  set CFG_OPTS=!CFG_OPTS! !DO_NOT_REUSE_ME!
)

rem FIRST we load a config file of extra options (if there is one)
set "CFG_FILE_HOME=%UserProfile%\.activator\activatorconfig.txt"
set "CFG_FILE_VERSION=%UserProfile%\.activator\%APP_VERSION%\activatorconfig.txt"
if exist %CFG_FILE_VERSION% (
  FOR /F "tokens=* eol=# usebackq delims=" %%i IN ("%CFG_FILE_VERSION%") DO (
    set DO_NOT_REUSE_ME=%%i
    rem ZOMG (Part #2) WE use !! here to delay the expansion of
    rem CFG_OPTS, otherwise it remains "" for this loop.
    set CFG_OPTS=!CFG_OPTS! !DO_NOT_REUSE_ME!
  )
)
if "%CFG_OPTS%"=="" (
  if exist %CFG_FILE_HOME% (
    FOR /F "tokens=* eol=# usebackq delims=" %%i IN ("%CFG_FILE_HOME%") DO (
      set DO_NOT_REUSE_ME=%%i
      rem ZOMG (Part #2) WE use !! here to delay the expansion of
      rem CFG_OPTS, otherwise it remains "" for this loop.
      set CFG_OPTS=!CFG_OPTS! !DO_NOT_REUSE_ME!
    )
  )
)

rem We use the value of the JAVACMD environment variable if defined
set _JAVACMD=%JAVACMD%

if "%_JAVACMD%"=="" (
  if not "%JAVA_HOME%"=="" (
    if exist "%JAVA_HOME%\bin\java.exe" set "_JAVACMD=%JAVA_HOME%\bin\java.exe"

    rem if there is a java home set we make sure it is the first picked up when invoking 'java'
    SET "PATH=%JAVA_HOME%\bin;%PATH%"
  )
)

if "%_JAVACMD%"=="" set _JAVACMD=java

rem Detect if this java is ok to use.
for /F %%j in ('"%_JAVACMD%" -version  2^>^&1') do (
  if %%~j==java set JAVAINSTALLED=1
  if %%~j==openjdk set JAVAINSTALLED=1
)

rem Detect the same thing about javac
if "%_JAVACCMD%"=="" (
  if not "%JAVA_HOME%"=="" (
    if exist "%JAVA_HOME%\bin\javac.exe" set "_JAVACCMD=%JAVA_HOME%\bin\javac.exe"
  )
)
if "%_JAVACCMD%"=="" set _JAVACCMD=javac
for /F %%j in ('"%_JAVACCMD%" -version 2^>^&1') do (
  if %%~j==javac set JAVACINSTALLED=1
)

rem BAT has no logical or, so we do it OLD SCHOOL! Oppan Redmond Style
set JAVAOK=true
if not defined JAVAINSTALLED set JAVAOK=false
if not defined JAVACINSTALLED set JAVAOK=false

if "%JAVAOK%"=="false" (
  echo.
  echo A Java JDK is not installed or can't be found.
  if not "%JAVA_HOME%"=="" (
    echo JAVA_HOME = "%JAVA_HOME%"
  )
  echo.
  echo Please go to
  echo   http://www.oracle.com/technetwork/java/javase/downloads/index.html
  echo and download a valid Java JDK and install before running Activator.
  echo.
  echo If you think this message is in error, please check
  echo your environment variables to see if "java.exe" and "javac.exe" are
  echo available via JAVA_HOME or PATH.
  echo.
  if defined DOUBLECLICKED pause
  exit /B 1
)

rem Check what Java version is being used to determine what memory options to use
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
)

rem Strips away the " characters
set JAVA_VERSION=%JAVA_VERSION:"=%

rem TODO Check if there are existing mem settings in JAVA_OPTS/CFG_OPTS and use those instead of the below
for /f "delims=. tokens=1-3" %%v in ("%JAVA_VERSION%") do (
    set MAJOR=%%v
    set MINOR=%%w
    set BUILD=%%x

    set META_SIZE=-XX:MetaspaceSize=64M -XX:MaxMetaspaceSize=256M
    if "!MINOR!" LSS "8" (
      set META_SIZE=-XX:PermSize=64M -XX:MaxPermSize=256M
    )

    set MEM_OPTS=!META_SIZE!
 )

rem We use the value of the JAVA_OPTS environment variable if defined, rather than the config.
set _JAVA_OPTS=%JAVA_OPTS%
if "%_JAVA_OPTS%"=="" set _JAVA_OPTS=%CFG_OPTS%

set DEBUG_OPTS=

rem Loop through the arguments, building remaining args in args variable
set args=
:argsloop
if not "%~1"=="" (
  rem Checks if the argument contains "-D" and if true, adds argument 1 with 2 and puts an equal sign between them.
  rem This is done since batch considers "=" to be a delimiter so we need to circumvent this behavior with a small hack.
  set arg1=%~1
  if "!arg1:~0,2!"=="-D" (
     set "args=%args% "%~1"="%~2""
    shift
    shift
    goto argsloop
  )

  if "%~1"=="-jvm-debug" (
    if not "%~2"=="" (
      rem This piece of magic somehow checks that an argument is a number
      for /F "delims=0123456789" %%i in ("%~2") do (
        set var="%%i"
      )
      if defined var (
        rem Not a number, assume no argument given and default to 9999
        set JPDA_PORT=9999
      ) else (
        rem Port was given, shift arguments
        set JPDA_PORT=%~2
        shift
      )
    ) else (
      set JPDA_PORT=9999
    )
    shift

    set DEBUG_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=!JPDA_PORT!
    goto argsloop
  )
  rem else
  set "args=%args% "%~1""
  shift
  goto argsloop
)

:run

if "!args!"=="" (
  if defined DOUBLECLICKED (
    set CMDS="ui"
  ) else set CMDS=!args!
) else set CMDS=!args!

rem We add a / in front, so we get file:///C: instead of file://C:
rem Java considers the later a UNC path.
rem We also attempt a solid effort at making it URI friendly.
rem We don't even bother with UNC paths.
set JAVA_FRIENDLY_HOME_1=/!ACTIVATOR_HOME:\=/!
set JAVA_FRIENDLY_HOME=/!JAVA_FRIENDLY_HOME_1: =%%20!

rem Checks if the command contains spaces to know if it should be wrapped in quotes or not
set NON_SPACED_CMD=%_JAVACMD: =%
if "%_JAVACMD%"=="%NON_SPACED_CMD%" %_JAVACMD% %DEBUG_OPTS% %MEM_OPTS% %ACTIVATOR_OPTS% %SBT_OPTS% %_JAVA_OPTS% "-Dactivator.home=%JAVA_FRIENDLY_HOME%" -jar "%ACTIVATOR_HOME%\libexec\%ACTIVATOR_LAUNCH_JAR%" %CMDS%
if NOT "%_JAVACMD%"=="%NON_SPACED_CMD%" "%_JAVACMD%" %DEBUG_OPTS% %MEM_OPTS% %ACTIVATOR_OPTS% %SBT_OPTS% %_JAVA_OPTS% "-Dactivator.home=%JAVA_FRIENDLY_HOME%" -jar "%ACTIVATOR_HOME%\libexec\%ACTIVATOR_LAUNCH_JAR%" %CMDS%

if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end

@endlocal

exit /B %ERROR_CODE%
