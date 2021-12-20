mkdir tmp-context
cp -R ../scripts tmp-context/
cp -R ../configs tmp-context/
cp ../order-service/target/order-service-0.0.1.jar tmp-context/service.jar
docker build -f Dockerfile-order-service-from-script -t ecomm-order-service:0.0.1 tmp-context
rm -rf tmp-context
