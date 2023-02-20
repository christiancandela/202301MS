FROM maven:3.6-jdk-11 as build

WORKDIR /app

# Crea una primera capra para el cache de maven en el repositorio local (descarga las librerias).
ADD pom.xml .
RUN mvn package -Dmaven.test.skip -Declipselink.weave.skip

ADD src src
RUN mvn package -DskipTests


# 2nd stage, build the runtime image
FROM openjdk:11-jre-slim
WORKDIR /app

# Copy the binary built in the 1st stage
COPY --from=build /app/target/cliente-socket-1.0.jar ./

CMD ["java", "-jar", "cliente-socket-1.0.jar"]