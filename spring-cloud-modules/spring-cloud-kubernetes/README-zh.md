# Spring Cloud Kubernetes

This module contains articles about Spring Cloud Kubernetes.

## Spring Cloud Kubernetes指南

1. 概述

    当我们构建一个微服务解决方案时，[Spring Cloud](https://www.baeldung.com/spring-cloud-series)和[Kubernetes](https://www.baeldung.com/kubernetes)都是最佳解决方案，因为它们提供了解决最常见挑战的组件。然而，如果我们决定选择Kubernetes作为我们解决方案的主要容器管理器和部署平台，我们仍然可以主要通过[Spring Cloud Kubernetes](https://cloud.spring.io/spring-cloud-static/spring-cloud-kubernetes/2.1.0.RC1/single/spring-cloud-kubernetes.html)项目来使用Spring Cloud的有趣功能。

    这个相对较新的项目无疑为[Spring Boot](https://www.baeldung.com/spring-boot-start)应用提供了与Kubernetes的轻松集成。在开始之前，看看如何在Minikube（一个本地Kubernetes环境）上部署[Spring Boot应用程序](https://www.baeldung.com/spring-boot-minikube)可能会有所帮助。

    在本教程中，我们将

    - 在我们的本地机器上安装Minikube
    - 开发一个微服务架构的例子，有两个独立的Spring Boot应用程序通过REST进行通信
    - 使用Minikube在一个单节点集群上设置应用程序
    - 使用YAML配置文件部署该应用程序
2. 场景

    在我们的例子中，我们使用的场景是旅行社向客户提供各种交易，客户会不时地查询旅行社服务。我们将用它来演示。

    - 通过Spring Cloud Kubernetes发现服务
    - 配置管理以及使用Spring Cloud Kubernetes Config注入Kubernetes ConfigMaps和secrets到应用pod中
    - 使用Spring Cloud Kubernetes Ribbon进行负载均衡
3. 环境设置

    首先，我们需要在本地机器上安装Minikube，最好是虚拟机驱动，如VirtualBox。另外，建议在进行这个环境设置之前，先看看Kubernetes及其主要功能。

    让我们启动本地单节点Kubernetes集群。

    `minikube start --vm-driver=virtualbox`

    该命令创建了一个虚拟机，使用VirtualBox驱动运行Minikube集群。kubectl中的默认上下文现在将是minikube。然而，为了能够在上下文之间切换，我们使用。

    `kubectl config use-context minikube`

    启动Minikube后，我们可以连接到Kubernetes仪表板，以访问日志并轻松监控我们的服务、pod、ConfigMaps和Secrets。

    `minikube dashboard`

3.1. 部署

首先，让我们从GitHub获取我们的例子。

这时，我们可以从父文件夹中运行 "deployment-travel-client.sh "脚本，或者逐一执行每条指令，以便很好地掌握程序。

###建立仓库
mvn clean install

### 设置docker环境
eval $(minikube docker-env)

###在minikube上构建docker镜像
cd travel-agency-service
docker build -t travel-agency-service .
cd .../client-service
docker build -t client-service .
cd ...。

###秘密和mongodb
kubectl delete -f travel-agency-service/secret.yaml
kubectl delete -f travel-agency-service/mongo-deployment.yaml

kubectl create -f travel-agency-service/secret.yaml
kubectl create -f travel-agency-service/mongo-deployment.yaml

### travel-agency-service
kubectl delete -f travel-agency-service/travel-agency-deployment.yaml
kubectl create -f travel-agency-service/travel-agency-deployment.yaml

###客户端服务
kubectl delete configmap client-service
kubectl delete -f client-service/client-service-deployment.yaml

kubectl create -f client-service/client-config.yaml
kubectl create -f client-service/client-service-deployment.yaml

# 检查pods是否正在运行
kubectl get pods
拷贝
4. 服务发现

这个项目为我们提供了Kubernetes中ServiceDiscovery接口的实现。在微服务环境中，通常有多个pod在运行同一个服务。Kubernetes将服务暴露为一个端点集合，可以从运行在同一Kubernetes集群的pod中的Spring Boot应用中获取和到达。

例如，在我们的例子中，我们有多个旅行社服务的副本，可以从我们的客户端服务访问http://travel-agency-service:8080。然而，这在内部会转化为访问不同的pod，如travel-agency-service-7c9cfff655-4hxnp。

Spring Cloud Kubernetes Ribbon使用这一功能在服务的不同端点之间进行负载平衡。

我们可以通过在客户端应用程序中添加spring-cloud-starter-kubernetes依赖项来轻松使用服务发现。

<依赖性
    <groupId>org.springframework.cloud</groupId>。
    <artifactId>spring-cloud-starter-kubernetes</artifactId>。
</dependency>
拷贝
此外，我们应该添加@EnableDiscoveryClient，并通过在我们的类中使用@Autowired将DiscoveryClient注入到ClientController中。

@SpringBootApplication
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args)。
    }
}
拷贝
@RestController
public class ClientController {
    @Autowired
    Private DiscoveryClient discoveryClient;
}
拷贝
5. 配置地图

通常情况下，微服务需要某种配置管理。例如，在Spring Cloud应用程序中，我们 

## Relevant Articles

- [Running Spring Boot Applications With Minikube](https://www.baeldung.com/spring-boot-minikube)
- [Self-Healing Applications with Kubernetes and Spring Boot](https://www.baeldung.com/spring-boot-kubernetes-self-healing-apps)
- [x] [Guide to Spring Cloud Kubernetes](https://www.baeldung.com/spring-cloud-kubernetes)
