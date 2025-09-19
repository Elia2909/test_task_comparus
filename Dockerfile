FROM gradle:jdk21 AS build
WORKDIR /app
COPY . .
RUN --mount=type=cache,target=/home/gradle/.gradle \
    gradle clean bootJar -x test

FROM eclipse-temurin:21-jre
ENV TZ=UTC \
    JAVA_TOOL_OPTIONS="-Duser.timezone=UTC"
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]