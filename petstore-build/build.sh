#!/bin/sh

echo "build maven packages..."
mvn clean package

echo "build docker images..."

for name in account config eureka gateway product search support web zipkin; do
  (cd ../petstore-$name && exec mvn docker:build)
done;

echo "start mysql..."

docker-compose up -d mysql

echo "start rabbitmq..."

docker-compose up -d rabbitmq

