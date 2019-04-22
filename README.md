# packer-assigment
- Sample application for packing used with knapsack dynamic programming algorithms.

## Getting started
### Prerequisites

- [Java 8](https://www.oracle.com/technetwork/java/javaee/downloads/jdk8-downloads-2133151.html)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-engine#/mac)

## Building the app
Run the following command to build the app:

    $ mvn clean install
    $ mvn clean package
    
If you want to create docker image. You can run below the instruction

    $ mvn clean package docker:build

## Running the app
Run the following command after building the app:

    $ cd packer-api/target/
    $ java -jar packer-api-0.0.1-SNAPSHOT.jar

If you want to use maven. You can run below the instruction

    $ cd packer-api
    $ mvn spring-boot:run
    
If you want to run on docker. You can run below the instruction after creating docker image.

    $ docker run -p 9002:9002 packer -t -d

## How to post input file to `/send-package` service
You may find the curl example post request on `/send-package` 
You can find the input file under sample folder.

`curl -X POST \
   http://localhost:9002/send-package \
   -H 'Content-Type: application/x-www-form-urlencoded' \
   -H 'Postman-Token: aaf30c33-70c7-4a55-8d01-2624d1fe9719' \
   -H 'cache-control: no-cache' \
   -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
   -F file=@/Users/kunter/workspace/packer-assigment/sample/data.txt`