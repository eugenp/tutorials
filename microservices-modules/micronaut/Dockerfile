FROM java:openjdk-8u111-alpine
RUN apk --no-cache add curl
COPY build/libs/*-all.jar hello-world-server.jar
CMD java ${JAVA_OPTS} -jar hello-world-server.jar