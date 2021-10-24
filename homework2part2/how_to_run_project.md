You can run project use Maven tool by running spring-boot:run command.

To verify that all works fine you can use [Postman collection "SecretProvider" that is here in repo](SecretProvider.postman_collection.json).

To create docker image you can run spring-boot:build-image command.

As a result you can see:
![Result of build-image command](DockerBuild.png?raw=true "Building Docker Image")

You can check the image running docker images command or using Docker Desktop (in case if you use Windows):
![Image in Docker Desktop Images tab](DockerImages.png?raw=true "Docker Images in Docker Desktop")


After successful building we can login in Docker and post our image to Docker Hub.