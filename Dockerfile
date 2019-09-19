FROM gradle:jdk11 as builder
COPY . /code

WORKDIR /code

# build the gradle cache
RUN gradle dependencies

RUN gradle build

# ---- backend -----

FROM openjdk:11-jre-stretch as backend
EXPOSE 8080
COPY --from=builder /code/build/libs/*-all.jar /app/app.jar
CMD [ "java", "-Dcom.sun.management.jmxremote", "-noverify", "${JAVA_OPTS}", "-jar", "/app/app.jar" ]
