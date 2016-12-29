#!/bin/sh

echo "Build Maven packages..."
# mvn clean package

echo "Build docker images..."

echo "Prepare volumes..."
mkdir -p ./mysql-data
chmod 777 ./mysql-data

echo "Pull 3rd-party images..."
docker pull redis:3.2
docker pull mysql:5.7

echo "Start mysql..."
docker-compose up -d mysql
