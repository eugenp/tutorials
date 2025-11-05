@echo off

setlocal

set NPMW_DIR=%~dp0

if exist "%NPMW_DIR%mvnw.cmd" (
  set NODE_EXE=^"^"
  set NODE_PATH=%NPMW_DIR%target\node\
  set NPM_EXE=^"%NPMW_DIR%target\node\npm.cmd^"
  set INSTALL_NPM_COMMAND=^"%NPMW_DIR%mvnw.cmd^" -Pwebapp frontend:install-node-and-npm@install-node-and-npm
) else (
  set NODE_EXE=^"%NPMW_DIR%build\node\bin\node.exe^"
  set NODE_PATH=%NPMW_DIR%build\node\bin\
  set NPM_EXE=^"%NPMW_DIR%build\node\lib\node_modules\npm\bin\npm-cli.js^"
  set INSTALL_NPM_COMMAND=^"%NPMW_DIR%gradlew.bat^" npmSetup
)

if not exist %NPM_EXE% (
  call %INSTALL_NPM_COMMAND%
)

if exist %NODE_EXE% (
  Rem execute local npm with local node, whilst adding local node location to the PATH for this CMD session
  endlocal & echo "%PATH%"|find /i "%NODE_PATH%;">nul || set "PATH=%NODE_PATH%;%PATH%" & call %NODE_EXE% %NPM_EXE% %*
) else if exist %NPM_EXE% (
  Rem execute local npm, whilst adding local npm location to the PATH for this CMD session
  endlocal & echo "%PATH%"|find /i "%NODE_PATH%;">nul || set "PATH=%NODE_PATH%;%PATH%" & call %NPM_EXE% %*
) else (
  call npm %*
)
