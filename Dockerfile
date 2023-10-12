FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} BirthdayNotification.jar
ENTRYPOINT ["java","-jar","/BirthdayNotification.jar"]


