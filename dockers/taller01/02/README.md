2. Descargue la imagen oficial de apache y cree un contenedor a partir de ella, tras crear el contenedor deberá poder acceder a la página web de inicio de dicho contenedor desde su equipo local.

R./
El proceso de descargar la imagen de apache esta documentado [aquí](https://hub.docker.com/_/httpd)

Para descargar la imágen de apache puede ejecutar el siguiente comando

```shell
docker pull httpd
```

Para descargar crear un contenedor a partir de la imágen puede ejecutar el siguiente comando

```shell
docker run -dit --name my-apache-app -p 8080:80 httpd
```

Y puede acceder a la página en la siguiente [url](http://localhost:8080)


