#!/bin/sh

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# -----------------------------------------------------------------------------
# Commons Daemon wrapper script.
# -----------------------------------------------------------------------------
#
# resolve links - $0 may be a softlink
ARG0="$0"
while [ -h "$ARG0" ]; do
  ls=`ls -ld "$ARG0"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    ARG0="$link"
  else
    ARG0="`dirname $ARG0`/$link"
  fi
done
DIRNAME="`dirname $ARG0`"
PROGRAM="`basename $ARG0`"
while [ ".$1" != . ]
do
  case "$1" in
    --java-home )
        JAVA_HOME="$2"
        shift; shift;
        continue
    ;;
    --catalina-home )
        CATALINA_HOME="$2"
        shift; shift;
        continue
    ;;
    --catalina-base )
        CATALINA_BASE="$2"
        shift; shift;
        continue
    ;;
    --catalina-pid )
        CATALINA_PID="$2"
        shift; shift;
        continue
    ;;
    --tomcat-user )
        TOMCAT_USER="$2"
        shift; shift;
        continue
    ;;
    --service-start-wait-time )
        SERVICE_START_WAIT_TIME="$2"
        shift; shift;
        continue
    ;;
    * )
        break
    ;;
  esac
done
# OS specific support (must be 'true' or 'false').
cygwin=false;
darwin=false;
case "`uname`" in
    CYGWIN*)
        cygwin=true
        ;;
    Darwin*)
        darwin=true
        ;;
esac

# Use the maximum available, or set MAX_FD != -1 to use that
test ".$MAX_FD" = . && MAX_FD="maximum"
# Setup parameters for running the jsvc
#
test ".$TOMCAT_USER" = . && TOMCAT_USER=tomcat
# Set JAVA_HOME to working JDK or JRE
# JAVA_HOME=/opt/jdk-1.6.0.22
# If not set we'll try to guess the JAVA_HOME
# from java binary if on the PATH
#
if [ -z "$JAVA_HOME" ]; then
    JAVA_BIN="`which java 2>/dev/null || type java 2>&1`"
    while [ -h "$JAVA_BIN" ]; do
        ls=`ls -ld "$JAVA_BIN"`
        link=`expr "$ls" : '.*-> \(.*\)$'`
        if expr "$link" : '/.*' > /dev/null; then
            JAVA_BIN="$link"
        else
            JAVA_BIN="`dirname $JAVA_BIN`/$link"
        fi
    done
    test -x "$JAVA_BIN" && JAVA_HOME="`dirname $JAVA_BIN`"
    test ".$JAVA_HOME" != . && JAVA_HOME=`cd "$JAVA_HOME/.." >/dev/null; pwd`
else
    JAVA_BIN="$JAVA_HOME/bin/java"
fi

# Only set CATALINA_HOME if not already set
test ".$CATALINA_HOME" = . && CATALINA_HOME=`cd "$DIRNAME/.." >/dev/null; pwd`
test ".$CATALINA_BASE" = . && CATALINA_BASE="$CATALINA_HOME"
test ".$CATALINA_MAIN" = . && CATALINA_MAIN=org.apache.catalina.startup.Bootstrap
# If not explicitly set, look for jsvc in CATALINA_BASE first then CATALINA_HOME
if [ -z "$JSVC" ]; then
    JSVC="$CATALINA_BASE/bin/jsvc"
    if [ ! -x "$JSVC" ]; then
        JSVC="$CATALINA_HOME/bin/jsvc"
    fi
fi
# Set the default service-start wait time if necessary
test ".$SERVICE_START_WAIT_TIME" = . && SERVICE_START_WAIT_TIME=10

# Ensure that any user defined CLASSPATH variables are not used on startup,
# but allow them to be specified in setenv.sh, in rare case when it is needed.
CLASSPATH=
JAVA_OPTS=
if [ -r "$CATALINA_BASE/bin/setenv.sh" ]; then
  . "$CATALINA_BASE/bin/setenv.sh"
elif [ -r "$CATALINA_HOME/bin/setenv.sh" ]; then
  . "$CATALINA_HOME/bin/setenv.sh"
fi

# Add on extra jar files to CLASSPATH
test ".$CLASSPATH" != . && CLASSPATH="${CLASSPATH}:"
CLASSPATH="$CLASSPATH$CATALINA_HOME/bin/bootstrap.jar:$CATALINA_HOME/bin/commons-daemon.jar"

test ".$CATALINA_OUT" = . && CATALINA_OUT="$CATALINA_BASE/logs/catalina-daemon.out"
test ".$CATALINA_TMP" = . && CATALINA_TMP="$CATALINA_BASE/temp"

# Add tomcat-juli.jar to classpath
# tomcat-juli.jar can be over-ridden per instance
if [ -r "$CATALINA_BASE/bin/tomcat-juli.jar" ] ; then
  CLASSPATH="$CLASSPATH:$CATALINA_BASE/bin/tomcat-juli.jar"
else
  CLASSPATH="$CLASSPATH:$CATALINA_HOME/bin/tomcat-juli.jar"
fi
# Set juli LogManager config file if it is present and an override has not been issued
if [ -z "$LOGGING_CONFIG" ]; then
  if [ -r "$CATALINA_BASE/conf/logging.properties" ]; then
    LOGGING_CONFIG="-Djava.util.logging.config.file=$CATALINA_BASE/conf/logging.properties"
  else
    # Bugzilla 45585
    LOGGING_CONFIG="-Dnop"
  fi
fi

test ".$LOGGING_MANAGER" = . && LOGGING_MANAGER="-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager"
JAVA_OPTS="$JAVA_OPTS $LOGGING_MANAGER"

# Set -pidfile
test ".$CATALINA_PID" = . && CATALINA_PID="$CATALINA_BASE/logs/catalina-daemon.pid"

# Increase the maximum file descriptors if we can
if [ "$cygwin" = "false" ]; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ "$?" -eq 0 ]; then
        # Darwin does not allow RLIMIT_INFINITY on file soft limit
        if [ "$darwin" = "true" -a "$MAX_FD_LIMIT" = "unlimited" ]; then
            MAX_FD_LIMIT=`/usr/sbin/sysctl -n kern.maxfilesperproc`
        fi
        test ".$MAX_FD" = ".maximum" && MAX_FD="$MAX_FD_LIMIT"
        ulimit -n $MAX_FD
        if [ "$?" -ne 0 ]; then
            echo "$PROGRAM: Could not set maximum file descriptor limit: $MAX_FD"
        fi
    else
        echo "$PROGRAM: Could not query system maximum file descriptor limit: $MAX_FD_LIMIT"
    fi
fi

# ----- Execute The Requested Command -----------------------------------------
case "$1" in
    run     )
      shift
      "$JSVC" $* \
      $JSVC_OPTS \
      -java-home "$JAVA_HOME" \
      -pidfile "$CATALINA_PID" \
      -wait "$SERVICE_START_WAIT_TIME" \
      -nodetach \
      -outfile "&1" \
      -errfile "&2" \
      -classpath "$CLASSPATH" \
      "$LOGGING_CONFIG" $JAVA_OPTS $CATALINA_OPTS \
      -Djava.endorsed.dirs="$JAVA_ENDORSED_DIRS" \
      -Dcatalina.base="$CATALINA_BASE" \
      -Dcatalina.home="$CATALINA_HOME" \
      -Djava.io.tmpdir="$CATALINA_TMP" \
      $CATALINA_MAIN
      exit $?
    ;;
    start   )
      "$JSVC" $JSVC_OPTS \
      -java-home "$JAVA_HOME" \
      -user $TOMCAT_USER \
      -pidfile "$CATALINA_PID" \
      -wait "$SERVICE_START_WAIT_TIME" \
      -outfile "$CATALINA_OUT" \
      -errfile "&1" \
      -classpath "$CLASSPATH" \
      "$LOGGING_CONFIG" $JAVA_OPTS $CATALINA_OPTS \
      -Djava.endorsed.dirs="$JAVA_ENDORSED_DIRS" \
      -Dcatalina.base="$CATALINA_BASE" \
      -Dcatalina.home="$CATALINA_HOME" \
      -Djava.io.tmpdir="$CATALINA_TMP" \
      $CATALINA_MAIN
      exit $?
    ;;
    stop    )
      "$JSVC" $JSVC_OPTS \
      -stop \
      -pidfile "$CATALINA_PID" \
      -classpath "$CLASSPATH" \
      -Djava.endorsed.dirs="$JAVA_ENDORSED_DIRS" \
      -Dcatalina.base="$CATALINA_BASE" \
      -Dcatalina.home="$CATALINA_HOME" \
      -Djava.io.tmpdir="$CATALINA_TMP" \
      $CATALINA_MAIN
      exit $?
    ;;
    version  )
      "$JSVC" \
      -java-home "$JAVA_HOME" \
      -pidfile "$CATALINA_PID" \
      -classpath "$CLASSPATH" \
      -errfile "&2" \
      -version \
      -check \
      $CATALINA_MAIN
      if [ "$?" = 0 ]; then
        "$JAVA_BIN" \
        -classpath "$CATALINA_HOME/lib/catalina.jar" \
        org.apache.catalina.util.ServerInfo
      fi
      exit $?
    ;;
    *       )
      echo "Unknown command: \`$1'"
      echo "Usage: $PROGRAM ( commands ... )"
      echo "commands:"
      echo "  run               Start Tomcat without detaching from console"
      echo "  start             Start Tomcat"
      echo "  stop              Stop Tomcat"
      echo "  version           What version of commons daemon and Tomcat"
      echo "                    are you running?"
      exit 1
    ;;
esac
