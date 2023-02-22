Con el fin de probar el funcionamiento de la comunicación entre contenedores se crearon la aplicación [servidor](./servidor) y [cliente](./cliente). Cada una de ellas con su respectivo Dockerfile.

Así mismo se creó un docker-compose para crear 3 contenedores, un servidor y dos clientes. Un cliente normal y un administrador asignando las correspondientes variables de entorno a cada uno de los contenedores. 

Para la generación de la aplicación java con el jar ejecutable se ha creado el archivo [pom](./pom.xml). Donde se ha adicionado los elementos necesarios para la creación de un jar ejecutable.

Así mismo, se creó un archivo [Dockerfile](./Dockerfile) con dos stage, el primero para la complicación y creación del jar, y el segundo para la creación de la imágen para la ejecución de la aplicación java.

```yaml
version: "3.7"
services:
  servidor_service:
    build: ./servidor
    restart: always
    ports:
      - "8080:80"
    environment:
      ROOT: prueba
  cliente_service:
    build: ./cliente
    restart: "no"
    depends_on:
      - servidor_service
    environment:
      SERVER: servidor_service
      CLIENTE: pedro
  administrador_service:
    build: ./cliente
    restart: "no"
    depends_on:
      - servidor_service
    environment:
      SERVER: servidor_service
      CLIENTE: prueba
```

Con su docker-compose creado, ejecute los siguientes comandos

```shell
docker-compose up
```
