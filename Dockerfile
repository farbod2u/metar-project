FROM openjdk:18-slim-buster
COPY ./target/metar-1.0.jar  /home/metar-1.0.jar
CMD ["java", "-jar", "/home/metar-1.0.jar"]
