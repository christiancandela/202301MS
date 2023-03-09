# Seguridad - API

## Introduction

Ejemplo generado usando microprofile, de forma particular la implementación de payaramicro.

## Tools
- java 11
- maven 3.8
- microprofile 6.0
- payaramicro 6.2023.2

## compile and build

La compilación y construcción se realiza mediante el siguiente comando

```shell
    mvn clean package
```

El comando genera un jar denominado  **autenticacion-microbundle.jar** tipicamente en la carpeta target.


## Run

Finalmente se puede proceder con la ejecución de la aplicación.

```shell  
java -jar target/autenticacion-microbundle.jar --nocluster
```




Puede ver la aplicación en la URL

   [http://localhost:8080/index.html](http://localhost:8080/index.html)  

## Test

### login 

Solicitud correcta
```shell  
curl -i -X POST http://localhost:8080/api/tokens -H 'Content-Type: application/json' -d '{"usuario":"pedro","clave":"pedro"}'
```

```shell  
curl -i -X POST http://localhost:8080/api/tokens -H 'Content-Type: application/json' -d '{"usuario":"pedro","clave":"juan"}'
```


### logout

```shell  
curl -i -H"'Authorization: Bearer ${token}'" DELETE http://localhost:8080/api/tokens/${token}  
```

### check token

```shell  
curl -i -X GET http://localhost:8080/api/tokens/${token} 
```

### list

```shell  
curl -i -X GET http://localhost:8080/api/tokens/ 
```
