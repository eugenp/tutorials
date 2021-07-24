Simple example showing how the Spring Gateway can be configured to use Dapr to 
proxy requests to back-end services.

# Building

Use Maven to build: 

    mvn clean install

# Running

Without Dapr:

    java -jar greeting/target/greeting-1.0-SNAPSHOT.jar
    java -Dspring.profiles.active=no-dapr -jar gateway/target/gateway-1.0-SNAPSHOT.jar

With basic Dapr:

    java -jar greeting/target/greeting-1.0-SNAPSHOT.jar
    dapr run --app-id greeting --dapr-http-port 4001 --app-port 3001 --config dapr-config/basic-config.yaml
    java -Dspring.profiles.active=with-dapr -jar gateway/target/gateway-1.0-SNAPSHOT.jar
    dapr run --app-id gateway --dapr-http-port 4000 --app-port 3000 --config dapr-config/basic-config.yaml

With Dapr, Consul integration:

    java -jar greeting/target/greeting-1.0-SNAPSHOT.jar
    dapr run --app-id greeting --dapr-http-port 4001 --app-port 3001 --config dapr-config/consul-config.yaml
    java -Dspring.profiles.active=with-dapr -jar gateway/target/gateway-1.0-SNAPSHOT.jar
    dapr run --app-id gateway --dapr-http-port 4000 --app-port 3000 --config dapr-config/consul-config.yaml

With Dapr, Zipkin integration:

    java -jar greeting/target/greeting-1.0-SNAPSHOT.jar
    dapr run --app-id greeting --dapr-http-port 4001 --app-port 3001 --config dapr-config/consul-zipkin-config.yaml
    java -Dspring.profiles.active=with-dapr -jar gateway/target/gateway-1.0-SNAPSHOT.jar
    dapr run --app-id gateway --dapr-http-port 4000 --app-port 3000 --config dapr-config/consul-zipkin-config.yaml
