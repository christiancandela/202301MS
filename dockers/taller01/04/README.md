Cree un contenedor con apache y php, para probar su funcionamiento cree una página php.

R./
El proceso de creación de un contenedor con apache y php esta documentado [aquí](https://hub.docker.com/_/php)


Para este punto se puede hacer uso de un Dockerfile así:

```dockerfile
FROM php:7.2-apache
COPY src/ /var/www/html/
```

Con su Dockerfile creado ejecute los siguientes comandos

```shell
docker build -t my-apache2-php .
docker run -dit --name my-running-phpapp -p 8080:80 my-apache2-php
```

Y puede acceder a la página en la siguiente [url](http://localhost:8080)
