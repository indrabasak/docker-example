# The FROM instruction sets the Base Image for subsequent instructions.
# FROM must be the first non-comment instruction in the Dockerfile.
FROM frolvlad/alpine-oraclejdk8:slim
# VOLUME instruction creates a mount point with the specified name
VOLUME /tmp
# ADD instruction copies from <src> and adds them to the filesystem of the image at the path <dest>
ADD docker-example-service-1.0.jar app.jar
# RUN instruction executes the command on top of the current image
RUN sh -c 'touch /app.jar'
# EXPOSE instruction informs Docker that the container listens on the specified network ports at runtime
EXPOSE 8080
ENV JAVA_OPTS=""
# ENTRYPOINT allows you to configure a container that will run as an executable
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dapp.port=${app.port}", "-jar","/app.jar"]
# ENTRYPOINT ["java -Djava.security.egd=file:/dev/./urandom -Dapp.port=${app.port} -jar /app.jar"]
# ENTRYPOINT ["java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dapp.port=${app.port} -jar /app.jar"]
# LABEL instruction adds metadata to an image
LABEL maintainer "Indra Basak"