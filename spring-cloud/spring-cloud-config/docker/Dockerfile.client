FROM alpine-java:base
MAINTAINER baeldung.com
RUN apk --no-cache add netcat-openbsd
COPY files/config-client.jar /opt/spring-cloud/lib/
COPY files/config-client-entrypoint.sh /opt/spring-cloud/bin/
RUN chmod 755 /opt/spring-cloud/bin/config-client-entrypoint.sh
