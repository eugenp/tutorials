FROM openjdk:11
RUN mkdir app
COPY service.jar app/service.jar
COPY scripts/* app/
COPY configs/* app/configs/
WORKDIR app
CMD bash ./start.sh
