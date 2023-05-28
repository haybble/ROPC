FROM adoptopenjdk/openjdk8
LABEL authors="haybble"

RUN mkdir app

WORKDIR /app

COPY target/ropc-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "ropc-0.0.1-SNAPSHOT.jar"]