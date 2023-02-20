Para la generación de la aplicación java con el jar ejecutable se ha creado el archivo [pom](./pom.xml). Donde se ha adicionado los elementos necesarios para la creación de un jar ejecutable.

Así mismo, se creó un archivo [Dockerfile](./Dockerfile) con dos stage, el primero para la complicación y creación del jar, y el segundo para la creación de la imágen para la ejecución de la aplicación java. 

```dockerfile
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
COPY --from=build /app/target/servidor-socket-1.0.jar ./

CMD ["java", "-jar", "servidor-socket-1.0.jar"]
```

Con su Dockerfile creado, ejecute los siguientes comandos

```shell
docker build -t my-server .
docker run -dit --name my-serverapp -p 8080:80 my-server
```

Y puede acceder a la página en la siguiente [url](http://localhost:8080)
