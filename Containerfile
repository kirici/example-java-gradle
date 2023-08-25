# syntax=docker/dockerfile:1
FROM docker.io/library/gradle:8.3.0-jdk20-jammy AS base
ENV GRADLE_USER_HOME="${PWD}"/.gradle
WORKDIR /home/project
COPY ./gradle ./gradle
COPY ./gradlew .
RUN ./gradlew --version
#------------------------------#
FROM base AS build
ENV GRADLE_USER_HOME="${PWD}"/.gradle
WORKDIR /home/project
COPY --from=base /home/project .
COPY . .
RUN ./gradlew build --no-daemon
#------------------------------#
FROM docker.io/library/eclipse-temurin:20-jdk-jammy
WORKDIR /home/project
COPY --from=build /home/project/app/build/libs/app.jar .
CMD ["java", "-jar", "app.jar"]

