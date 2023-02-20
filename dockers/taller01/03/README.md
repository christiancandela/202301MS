Cree una página html con mensaje de bienvenida y el logo de la universidad. Cópiela en su contenedor y verifique que puede acceder a ella mediante un navegador. De igual forma trate de acceder directamente al logo de la universidad alojado en el contenedor por medio de su navegador.

R./
El proceso de descargar la imagen de apache esta documentado [aquí](https://hub.docker.com/_/httpd)


Para este punto se puede hacer uso de un Dockerfile así:

```dockerfile
FROM httpd:2.4
COPY src/ /usr/local/apache2/htdocs/
```

Con su Dockerfile creado ejecute los siguientes comandos

```shell
docker build -t my-apache2 .
docker run -dit --name my-running-app -p 8080:80 my-apache2
```

Y puede acceder a la página en la siguiente [url](http://localhost:8080)
