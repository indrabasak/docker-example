[![Build Status][travis-badge]][travis-badge-url]

Spring Boot Example with Docker
==================================
This example creates a [`Docker`](https://www.docker.com/) image from a [`Spring Boot`](https://projects.spring.io/spring-boot/) 
application using [`Spotify's Docker Maven plugin`](https://github.com/spotify/docker-maven-plugin).

## Modules
The project consist of two modules:

1. `docker-example-model` - contains all the models used in the example
2. `docker-spring-service` - example Spring Boot based microservice project

## Build and Run
### Maven Build
Make sure you have `Maven` installed. Execute the following maven command from the directory of the 
parent project, `docker-example`:
```
mvn clean install
```
It will create the Spring Boot executable JAR,`docker-example-service-1.0.jar`, under `docker-example-service/target` 
folder.

### Run
To the newly created Spring Boot JAR from the terminal:
```
java -jar docker-example-service-1.0.jar
```
This should start up the example application at port `8080`. The application can be accessed at `http://localhost:8080`

### Docker Build
Before you build the Docker image, make sure Docker is available in your environment.
Execute the following maven command from the directory of the parent project, `docker-example`:
```
mvn clean package docker:build
```
This should build a Docker image named `docker-example`

### Docker Run
Run yor newly created Docker image, `docker-example`, by executing the 
[`docker run`](https://docs.docker.com/engine/reference/run/) command from the terminal:
```
docker run --rm -p 8080:8080  --name=cheetos docker-example
```
##### Options
* The `--rm` option automatically clean up the container and remove the file system when the container exit.
* The `--name` option names the Docker container as `cheetos`. In absence of the `--name` option, the Docker generates a 
random name for your container.
* The [`-p 8080:8080`](https://docs.docker.com/engine/reference/run/#expose-incoming-ports) option publishes all 
exposed ports to the host interfaces. In our example, it is port `8080` is both `hostPort` and `containerPort` 

This should start up the example application and it can be accessed at `http://localhost:8080`

## Docker Commands
### List Container
Run the [`docker ps`](https://docs.docker.com/v1.11/engine/reference/commandline/ps/) to list all the containers.
To see all running containers, execute the following command:
```
bash-3.2$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
d03854fb7779        docker-example      "java -Djava.security"   7 seconds ago       Up 6 seconds        0.0.0.0:8080->8080/tcp   cheetos
bash-3.2$ 
```
To see all running containers including the non-running ones, execute the following command:
```
bash-3.2$ docker ps -a
CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS                         PORTS                    NAMES
d03854fb7779        docker-example      "java -Djava.security"   About a minute ago   Up About a minute              0.0.0.0:8080->8080/tcp   cheetos
28b2cff9e7e6        docker-example      "java -Djava.security"   About an hour ago    Exited (0) About an hour ago                            indra1
d2720676c932        nginx               "nginx -g 'daemon off"   4 months ago         Exited (0) 4 months ago                                 webserver
```

### Remove Container
To remove a Docker container, execute [`docker rm`](https://docs.docker.com/v1.11/engine/reference/commandline/rm/) 
command. This will remove a non-running container.
```
bash-3.2$ docker rm indra1
indra1 
```
To forcefully remove a running container
```
bash-3.2$ docker rm -f cheetos
cheetos
```

### Stop Container
To stop a container, execute [`docker stop`](https://docs.docker.com/v1.11/engine/reference/commandline/stop/)
```
command:
bash-3.2$ docker stop cheetos
cheetos
```

[travis-badge]: https://travis-ci.org/indrabasak/docker-example.svg?branch=master
[travis-badge-url]: https://travis-ci.org/indrabasak/docker-example/