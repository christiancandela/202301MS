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
Solicitud con respuesta 401
```shell  
curl -i -X POST http://localhost:8080/api/tokens -H 'Content-Type: application/json' -d '{"usuario":"pedro","clave":"juan"}'
```
Solicitud con respuesta 400
```shell  
curl -i -X POST http://localhost:8080/api/tokens -H 'Content-Type: application/json' -d '{}'
```


### logout
Solicitud correcta
```shell  
curl -i -H "Authorization: Bearer ${token}" -X DELETE http://localhost:8080/api/tokens/${token}  
```
Solicitud 401
```shell  
curl -i -X DELETE http://localhost:8080/api/tokens/123  
```
```shell  
curl -i -H "Authorization: Bearer 123" -X DELETE http://localhost:8080/api/tokens/123  
```
Solicitud 403
```shell  
curl -i -H "Authorization: Bearer ${token}" -X DELETE http://localhost:8080/api/tokens/123  
```


### check token
Solicitud correcta
```shell  
curl -i -X GET http://localhost:8080/api/tokens/${token} 
```
Solicitud 404
```shell  
curl -i -X GET http://localhost:8080/api/tokens/123  
```

### list

```shell  
curl -i -X GET http://localhost:8080/api/tokens/ 
```
