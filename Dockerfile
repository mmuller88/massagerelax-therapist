FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8081
ADD therapist-0.0.1-SNAPSHOT.jar therapist.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /therapist.jar" ]