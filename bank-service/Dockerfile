FROM openjdk:11-jdk-slim AS build

WORKDIR /opt/app
COPY . .
RUN ./mvnw clean install


FROM openjdk:11-jre
COPY --from=build opt/app/target/*.jar ./app.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8787", "-Dcom.sun.management.jmxremote", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false", "-Dcom.sun.management.jmxremote.port=1099", "-Dcom.sun.management.jmxremote.rmi.port=1098", "-Djava.rmi.server.hostname=localhost", "-jar", "app.jar"]