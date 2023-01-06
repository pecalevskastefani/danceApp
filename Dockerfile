FROM openjdk:8
EXPOSE 9091
ADD target/danceapp.jar danceapp.jar
ENTRYPOINT ["java","-jar","/danceapp.jar"]