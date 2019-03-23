#Kotlin #MySQL #SpringBoot 
=============


Therapist service for with several endpoints.


### How to run?

* Make sure you have mysql server up and running on `localhost`
* `docker run -p 3306:3306 --env MYSQL_ROOT_PASSWORD=massagerelax --env MYSQL_USER=massagerelax --env MYSQL_DATABASE=massagerelax --env MYSQL_PASSWORD=massagerelax --name mysql_massagerelax mysql:5.7.23 `
* Start application with `./gradlew bootRun`

### Create Docker Image
* `./gradlew build buildDocker`

### Push Docker image
* `./gradlew build releaseDocker`

### API Documentation

Docs can be found in swagger. Head to [/swagger-schema](http://localhost:8081/swagger-schema)
after server boots for raw schema or checkout [SwaggerUI](http://localhost:8081/swagger-ui.html) for nice interface

-------------

Links
* [Author's blog](http://martin--mueller.com)
* [GitHub repo](https://github.com/mmuller88/massagerelax-therapist)

