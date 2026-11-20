@echo off
setlocal EnableDelayedExpansion
setlocal EnableExtensions



set MVNW=mvnw.cmd

SET CMD=%1
if "%CMD%"=="" (
  set CMD=jar
)
goto %CMD%

goto :EOF
:mac_desktop
!MVNW! package -DskipTests -Dcodename1.platform^=javase -Dcodename1.buildTarget^=mac-os-x-desktop -U -e

goto :EOF
:windows_desktop
!MVNW! package -DskipTests -Dcodename1.platform^=javase -Dcodename1.buildTarget^=windows-desktop -U -e

goto :EOF
:windows_device
!MVNW! package -DskipTests -Dcodename1.platform^=win -Dcodename1.buildTarget^=windows-device -U -e

goto :EOF
:uwp
set /a _0_%~2=^(1 + %~2^)
call :windows_device _1_%~2 !_0_%~2!
echo | set /p ^=!_1_%~2!

goto :EOF
:javascript
!MVNW! package -DskipTests -Dcodename1.platform^=javascript -Dcodename1.buildTarget^=javascript -U -e

goto :EOF
:android
!MVNW! package -DskipTests -Dcodename1.platform^=android -Dcodename1.buildTarget^=android-device -U -e

goto :EOF
:xcode
!MVNW! package -DskipTests -Dcodename1.platform^=ios -Dcodename1.buildTarget^=ios-source -U -e

goto :EOF
:ios_source
set /a _0_%~2=^(1 + %~2^)
call :xcode _1_%~2 !_0_%~2!
echo | set /p ^=!_1_%~2!

goto :EOF
:android_source
!MVNW! package -DskipTests -Dcodename1.platform^=android -Dcodename1.buildTarget^=android-source -U -e

goto :EOF
:ios
!MVNW! package -DskipTests -Dcodename1.platform^=ios -Dcodename1.buildTarget^=ios-device -U -e

goto :EOF
:ios_release
!MVNW! package -DskipTests -Dcodename1.platform^=ios -Dcodename1.buildTarget^=ios-device-release -U -e

goto :EOF
:jar
!MVNW! -Pexecutable-jar package -Dcodename1.platform^=javase -DskipTests -U -e

goto :EOF
:help
echo build.sh [COMMAND]
echo Local Build Commands:
echo   The following commands will build the app locally ^(i.e. does NOT use the Codename One build server^)
echo 
echo   jar
echo     Builds app as desktop app executable jar file to javase/target directory
echo   android_source
echo     Generates an android gradle project that can be opened in Android studio
echo     *Requires android development tools installed.
echo     *Requires ANDROID_HOME environment variable
echo     *Requires either GRADLE_HOME environment variable^, or for gradle to be in PATH
echo   ios_source
echo     Generates an Xcode Project that you can open and build using Apple^'s development tools
echo     *Requires a Mac with Xcode installed
echo 
echo Build Server Commands:
echo   The following commands will build the app using the Codename One build server^, and require
echo   a Codename One account.  See https://www.codenameone.com
echo 
echo   ios
echo     Builds iOS app.
echo   ios_release
echo     Builds iOS app for submission to Apple appstore.
echo   android
echo     Builds android app.
echo   mac_desktop
echo     Builds Mac OS desktop app.
echo     *Mac OS Desktop builds are a Pro user feature.
echo   windows_desktop
echo     Builds Windows desktop app.
echo     *Windows Desktop builds are a Pro user feature.
echo   windows_device
echo     Builds UWP Windows app.
echo   javascript
echo     Builds as a web app.
echo     *Javascript builds are an Enterprise user feature

goto :EOF
:settings
!MVNW! cn:settings -U -e
