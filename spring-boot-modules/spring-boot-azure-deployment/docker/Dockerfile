FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD azure-0.1.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
