#!/bin/sh

echo "build maven packages..."
mvn clean package

echo "build docker images..."

(cd ../petstore-account && exec mvn docker:build)
(cd ../petstore-config && exec mvn docker:build)
(cd ../petstore-eureka && exec mvn docker:build)
(cd ../petstore-gateway && exec mvn docker:build)
(cd ../petstore-product && exec mvn docker:build)
(cd ../petstore-search && exec mvn docker:build)
(cd ../petstore-support && exec mvn docker:build)
(cd ../petstore-web && exec mvn docker:build)

MYSQL_DATA_VOLUME="./mysql-data"

if [ -d $MYSQL_DATA_VOLUME ]; then
  echo "mysql data volume exist."
else
  echo "prepare mysql data volume..."
  mkdir -p $MYSQL_DATA_VOLUME
  chmod 777 $MYSQL_DATA_VOLUME
fi

echo "start mysql..."

docker-compose up -d mysql

echo "start rabbitmq..."

docker-compose up -d rabbitmq

