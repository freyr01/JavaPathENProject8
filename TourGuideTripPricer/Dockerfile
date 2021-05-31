FROM openjdk:8-jdk-alpine
LABEL maintainer="mathias.lauer.fr@gmail.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tourGuideTripPricer.jar
ENTRYPOINT ["java","-Duser.country=EN","-Duser.language=en","-jar","/tourGuideTripPricer.jar"]