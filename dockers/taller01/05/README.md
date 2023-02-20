Cree una página html con un formulario que solicite su nombre e identificación y envié la información mediante POST a una página php que reciba dicha información y genere un mensaje de bienvenida a partir de dichos datos.

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
