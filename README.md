# Yakshop

Repository containing all the source code for _Yakshop_, the Tundra's first home-grown webshop for all your yak produce.

## Project Kanban Board

[Project Kanban Board](https://trello.com/b/Vzj6KVcx/yakshop)

## Prerequisite
- [Docker](https://docs.docker.com/get-docker/)
- [Maven3](https://maven.apache.org/install.html)
- [java 11 (jre/jdk)](https://www.java.com/en/download/help/download_options.xml)
- A local copy of this repository

```shell
http -> git clone https://github.com/xebia/yakshop-iggydv.git
ssh  -> git clone git@github.com:xebia/yakshop-iggydv.git
```

## Building YakShop locally

This application is a spring-boot application built in Java 11, using maven as dependency and package manager.
This application is intended to be executed as a JAR. 

In order to build the application the following commands can be used:

```shell
mvn clean package
```
This will download and build all the required project dependencies and package it as a JAR file.

### Running YakShop locally

To run the application, simply use following command:
```shell
java -jar target\yakshop-0.0.1-SNAPSHOT.jar
```

## Building YakShop locally (Docker)

The application can also be executed as a docker container.

In order to build the docker image, the JAR needs to exist first. \
To ensure the image can be built, the following commands can be used:
```shell
mvn clean package
docker build . -t yakshop
```

### Running YakShop locally (Docker)
To run the application using docker, the following can be used:
```shell
docker run -p 8080:8080 -t yakshop
```

## No java, no problem
If you are unable to build the application locally, the docker image can be pulled directly from dockerhub.
```shell
docker pull iggydv12/yakshop
```
to run the container-image use
```shell
docker run -p 8080:8080 -t iggydv12/yakshop
```

## YakShop API

Once the application is up and running, the swagger API can be visited at http://localhost:8080/swagger-ui/index.html#/

The purpose of the application is to allow a heard of LabYaks to be loaded into the application via HTTP POST. 
Once the herd is loaded it can be queried and orders can be placed against the available stock.

Alternatively API endpoints can be accessed using curl commands.

**Load new herd:**
```shell
curl -X 'POST' \
  'http://localhost:8080/yak-shop/load' \
  -H 'accept: */*' \
  -H 'Content-Type: application/xml' \
  -d '<?xml version="1.0" encoding="UTF-8"?>
<herd>
<labyak name="Betty-1" age="4" sex="f"/>
<labyak name="Betty-2" age="8" sex="f"/>
<labyak name="Betty-3" age="9.5" sex="f"/>
</herd>'
```