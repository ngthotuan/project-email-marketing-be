FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN chmod +x ./mvnw
RUN ./mvnw clean install package -DskipTests=true


FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
WORKDIR /workspace/app
COPY --from=build /workspace/app/target/*.jar .
ENV DB_URL=jdbc:postgresql://localhost:5432/postgres
EXPOSE 8000

ENTRYPOINT ["java","-jar","/workspace/app/demo-0.0.1-SNAPSHOT.jar"]