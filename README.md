[![Build Status][travis-badge]][travis-badge-url]

Spring Boot Example with Docker
==================================
This example creates a [`Docker`](https://www.docker.com/) image from a [`Spring Boot`](https://projects.spring.io/spring-boot/) 
application using [`Spotify's Docker Maven plugin`](https://github.com/spotify/docker-maven-plugin).

## Modules
The project consist of two modules:

1. `docker-example-model` - contains all the models used in the example
2. `docker-spring-service` - contains Spring Boot example service and related classes

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
To run the newly created Spring Boot JAR from the terminal:
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
This should build a Docker image named `docker-example`.

### Docker Run
Run the newly created Docker image, `docker-example`, by executing the 
[`docker run`](https://docs.docker.com/engine/reference/run/) command from the terminal:
```
docker run --rm -p 8080:8080  --name=cheetos docker-example
```
##### Options
* `--rm` option automatically clean up the container and remove the file system when the container exit.
* `--name` option names the Docker container as `cheetos`. In absence of the `--name` option, the Docker generates a 
random name for your container.
* [`-p 8080:8080`](https://docs.docker.com/engine/reference/run/#expose-incoming-ports) option publishes all 
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

## Docker Maven Plugin Setup
We are using [`Spotify's Docker Maven Plugin`](https://github.com/spotify/docker-maven-plugin). It's relatively easy to
setup in your Maven `pom.xml`. Complexity rises when you specify the plugin in the parent POM. Here are the changes to
the parent `pom.xml`
```
    <build>
        <plugins>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.13</version>
                <configuration>
                    <skipDockerBuild>true</skipDockerBuild>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
The `skipDockerBuild` tag is set to `true` in order to skip the docker build in the parent pom.

Changes to the child `pom.xml` where Spring Boot JAR gets created:
```
    <build>
        <plugins>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <configuration>
                    <skipDockerBuild>false</skipDockerBuild>
                    <imageName>${docker.image.name}</imageName>
                    <dockerDirectory>${basedir}/src/main/docker</dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
Here is the `skipDockerBuild` tag is set to `false` to override the parent flag.

##### Configuration Tags
* `imageName` specifies the name of our example Docker image, e.g, `docker-example`
* `dockerDirectory` specifies the location of the `Dockerfile`. The contents of the dockerDirectory will be 
copied into `${project.build.directory}/docker`. A [`Dockerfile`](https://docs.docker.com/engine/reference/builder/)
specfies all the instructions to be read by Docker while building the image.
* `include` specfies the resources to be included, which ion our case is `docker-example-service-1.0.jar`

## DockerFile
Here is the content of the example `Dockerfile`.
```
FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD docker-example-service-1.0.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dapp.port=${app.port}", "-jar","/app.jar"]
LABEL maintainer "Indra Basak"
```

#### Instruction
* `FROM` instruction sets the Base Image for subsequent instructions. FROM must be the first non-comment 
instruction in the Dockerfile.
* `VOLUME` instruction creates a mount point with the specified name.
* `ADD` instruction copies from `<src>` and adds them to the filesystem of the image at the path `<dest>`.
* `RUN` instruction executes the command on top of the current image.
* `EXPOSE` instruction informs Docker that the container listens on the specified network ports at runtime.
* `ENV` instruction sets the environment variable
* `ENTRYPOINT` allows you to configure a container that will run as an executable.
* `LABEL` instruction adds metadata to an image.

You can find out more about Docker instructions [`here`](https://docs.docker.com/engine/reference/builder/#usage)

[travis-badge]: https://travis-ci.org/indrabasak/docker-example.svg?branch=master
[travis-badge-url]: https://travis-ci.org/indrabasak/docker-example/