# syntax=docker/dockerfile:1
FROM gradle:8.3.0-jdk17-jammy AS base
ENV GRADLE_USER_HOME="${PWD}"/.gradle

WORKDIR /app
COPY ./gradle ./gradle
COPY ./gradlew .
RUN ./gradlew --version

#------------------------------#
FROM base
ENV GRADLE_USER_HOME="${PWD}"/.gradle

WORKDIR /app
COPY --from=base /app .
COPY . .
