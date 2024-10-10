# eAdaptor Outbound Service

This service is an updated implementation of the eAdaptor Outbound Service. It is a RESTful service that provides a way
to send messages to a specified endpoint.
The service is built using the Spring Boot framework and is deployed as a Docker container.

* Docker Build Command:
```bash
docker build  -t diego005/diego:cargowise-eadaptor-service .
```

* Docker Run Command:
```bash
docker run -d \
  --name cargowise-eadaptor-service \
  -p 443:443 \
  -v C:/Users/diego/Desktop/DOCKER/eadaptor-service/logs:/app/logs \
  -v C:/Users/diego/Desktop/DOCKER/eadaptor-service/outbound:/app/outbound \
  -e EADAPTOR_OUTBOUND_FOLDER=./outbound \
  diego005/diego:cargowise-eadaptor-service
```
