FROM openjdk:8
VOLUME /tmp
ADD target/packer-api-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch app.jar'
ENTRYPOINT ["java","-jar","app.jar"]