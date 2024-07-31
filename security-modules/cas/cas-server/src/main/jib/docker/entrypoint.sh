#!/bin/sh

ENTRYPOINT_DEBUG=${ENTRYPOINT_DEBUG:-false}
JVM_DEBUG=${JVM_DEBUG:-false}
JVM_DEBUG_PORT=${JVM_DEBUG_PORT:-5000}
JVM_DEBUG_SUSPEND=${JVM_DEBUG_SUSPEND:-n}
JVM_MEM_OPTS=${JVM_MEM_OPTS:--Xms512m -Xmx4096M}
JVM_EXTRA_OPTS=${JVM_EXTRA_OPTS:--server -noverify -XX:+TieredCompilation -XX:TieredStopAtLevel=1}

if [ $JVM_DEBUG = "true" ]; then
  JVM_EXTRA_OPTS="${JVM_EXTRA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,address=*:${JVM_DEBUG_PORT},server=y,suspend=${JVM_DEBUG_SUSPEND}"
fi

if [ $ENTRYPOINT_DEBUG = "true" ]; then
  JVM_EXTRA_OPTS="${JVM_EXTRA_OPTS} -Ddebug=true"
  
  echo "\nChecking java..."
  java -version

  if [ -d /etc/cas ] ; then
    echo "\nListing CAS configuration under /etc/cas..."
    ls -R /etc/cas
  fi
  echo "\nRemote debugger configured on port ${JVM_DEBUG_PORT} with suspend=${JVM_DEBUG_SUSPEND}: ${JVM_DEBUG}"
  echo "\nJava args: ${JVM_MEM_OPTS} ${JVM_EXTRA_OPTS}"
fi

echo "\nRunning CAS @ cas.war"
# shellcheck disable=SC2086
exec java $JVM_EXTRA_OPTS $JVM_MEM_OPTS -jar cas.war "$@"
