FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx768m", "-Xms512m", "-Dserver.address=0.0.0.0", "-Dserver.port=8080", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.jmx.enabled=false", "-Dspring.main.lazy-initialization=false", "-jar", "app.jar"]