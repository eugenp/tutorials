FROM alpine-java:base
MAINTAINER baeldung.com
COPY files/config-server.jar /opt/spring-cloud/lib/
ENV SPRING_APPLICATION_JSON='{"spring": {"cloud": {"config": {"server": \
    {"git": {"uri": "/var/lib/spring-cloud/config-repo", "clone-on-start": true}}}}}}'
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/spring-cloud/lib/config-server.jar"]
VOLUME /var/lib/spring-cloud/config-repo
EXPOSE 8888
