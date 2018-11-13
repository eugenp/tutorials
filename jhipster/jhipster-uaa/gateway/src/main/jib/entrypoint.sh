#!/bin/sh

echo "The application will start in ${JHIPSTER_SLEEP}s..." && sleep ${JHIPSTER_SLEEP}
exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.baeldung.jhipster.gateway.GatewayApp"  "$@"
