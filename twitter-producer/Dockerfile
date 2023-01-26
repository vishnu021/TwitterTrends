FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/twitter-producer-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} twitter-producer.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","twitter-producer.jar"]