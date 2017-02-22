# use openjdk as base image
# check https://hub.docker.com/_/openjdk/
FROM openjdk:8-jre

# the executable jar file packaged by Spring Boot:
ARG jarfile

VOLUME /tmp

COPY $jarfile app.jar

CMD ["sh", "-c", "java -jar app.jar"]
