### set docker env
eval $(minikube docker-env)

### build the repository
#mvn clean  install

### build the docker images on minikube
cd travel-agency-service
docker build -t travel-agency-service .
cd ../client-service
docker build -t client-service .
cd ..

### secret and mongodb
kubectl delete -f travel-agency-service/secret.yaml
kubectl delete -f travel-agency-service/mongo-deployment.yaml

kubectl create -f travel-agency-service/secret.yaml
kubectl create -f travel-agency-service/mongo-deployment.yaml

### travel-agency-service
kubectl delete -f travel-agency-service/travel-agency-deployment.yaml
kubectl create -f travel-agency-service/travel-agency-deployment.yaml


### client-service
kubectl delete configmap client-service
kubectl delete -f client-service/client-service-deployment.yaml

kubectl create -f client-service/client-config.yaml
kubectl create -f client-service/client-service-deployment.yaml

# Check that the pods are running
kubectl get pods
