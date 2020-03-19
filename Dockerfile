#FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
#COPY target/simple-rest-streamer*.jar simple-rest-streamer.jar
#EXPOSE 8080
#CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar simple-rest-streamer.jar


# build stage
FROM maven:3-jdk-11 as builder
RUN mkdir -p /usr/src/app
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -DskipTests=true


# create Image stage
FROM adoptopenjdk/openjdk11-openj9:x86_64-ubuntu-jdk-11.0.6_10_openj9-0.18.1-slim

VOLUME /tmp

COPY --from=builder  /usr/src/app/target/simple-rest-streamer-*.jar ./simple-rest-streamer.jar

RUN sh -c 'touch ./simple-rest-streamer.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT}","-jar","./simple-rest-streamer.jar"]
