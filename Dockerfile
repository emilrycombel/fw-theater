FROM amazoncorretto:21

WORKDIR /app

COPY build/libs/fw-theater-0.0.1-SNAPSHOT.jar fw-theater-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "fw-theater-0.0.1-SNAPSHOT.jar"]