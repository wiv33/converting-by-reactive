FROM hirokimatsumoto/alpine-openjdk-11

MAINTAINER PS
VOLUME /tmp

# --build-arg build/libs/*.jar
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

#"-Djava.security.egd=file:/dev/./urandom",
#"-Dspring.profiles.active=prod",
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]