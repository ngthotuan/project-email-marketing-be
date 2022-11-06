FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
WORKDIR /app
COPY /target/*.jar .
ENV DB_DRIVER_CLASS_NAME=org.h2.Driver
ENV DB_URL=jdbc:h2:mem:mydb
ENV DB_USERNAME=emtest
ENV DB_PASSWORD=emtest
ENV SECRET=secret4684646684646846468488468suhfusdhfiseifhisifhisheifhsiehfihsihe
ENV THREAD_POOL_SIZE=5
ENV FILE_CONTROLLER_ENDPOINT=http://localhost:8080/email-service/v1
EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
