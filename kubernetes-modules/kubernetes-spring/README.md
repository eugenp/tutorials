# Kong Ingress Controller with Spring Boot

This project was generated from Spring Initializer Website (https://start.spring.io/): 

Maven version Java latest: 
```
curl https://start.spring.io/starter.tgz -d dependencies=webflux,actuator -d type=maven-project | tar -xzvf -
```

Maven version Java 11: 
```
curl https://start.spring.io/starter.tgz -d dependencies=webflux,actuator -d type=maven-project -d javaVersion=11 | tar -xzvf -
```

## Steps to run the demonstration:

1. Install minikube: https://www.baeldung.com/spring-boot-minikube

2. Install Kong Ingress Controller on minikube: https://docs.konghq.com/kubernetes-ingress-controller/latest/deployment/minikube/
- Test wich echo-server
- Create environment variable: export PROXY_IP=$(minikube service -n kong kong-proxy --url | head -1)

3. build that Spring boot service: 
- Run ./mvnw install
- Run jar: java -jar target/*.jar

4. Create a Docker image: 
If using minikube and don't want to push image to a repository, then point your local Docker client to Minikube's implementation: eval $(minikube -p minikube docker-env) --- use the same shell.
- Run: ./mvnw spring-boot:build-image

5. Deploy the application, create a service and an ingress rule: 
```
kubectl apply -f serviceDeployment.yaml
kubectl apply -f clusterIp.yaml
kubectl apply -f ingress-rule.yaml
```

6. Test access using the proxy IP: 
```
curl -i $PROXY_IP/actuator/health
```

## Setting up a rate limiter on your api

7. Create a plugin: 

kubectl apply -f rate-limiter.yaml

8. Now test resource. Try more than 5 times a minute: 
```
curl -i $PROXY_IP/actuator/health
```


## Relevant Articles
- [Kong Ingress Controller with Spring Boot](https://www.baeldung.com/spring-boot-kong-ingress)
