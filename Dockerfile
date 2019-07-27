FROM openjdk:8-jre-alpine

ENV LANG C.UTF-8
ENV EDITOR vim

WORKDIR /var/www
ADD ./build/libs/ktor-amp-application.jar /var/www/ktor-amp-application.jar
