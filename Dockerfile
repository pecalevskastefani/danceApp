FROM openjdk:8
EXPOSE 8080
ADD target/danceapp.jar danceapp.jar
ENTRYPOINT ["java","-jar","/danceapp.jar"]