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
ENV DB_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
ENV DB_URL=jdbc:mysql://localhost:3306/email_marketing
ENV DB_USERNAME=emtest
ENV DB_PASSWORD=emtest
ENV SECRET=secret4684646684646846468488468suhfusdhfiseifhisifhisheifhsiehfihsihe
ENV THREAD_POOL_SIZE=5
EXPOSE 8080

ENTRYPOINT ["java","-jar","/workspace/app/demo-0.0.1-SNAPSHOT.jar"]