FROM openjdk:11
RUN mkdir app
COPY payment-service/target/payment-service-0.0.1.jar app/service.jar
COPY scripts/* app/
COPY configs/* app/configs/
WORKDIR app
CMD bash ./start.sh
