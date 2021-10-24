You can run project use Maven tool by running spring-boot:run command.

To verify that all works fine you can use [Postman collection "SecretProvider" that is here in repo](SecretProvider.postman_collection.json).

To create docker image you can run spring-boot:build-image command.

As a result you can see:
![Alt text](DockerBuild.png?raw=true "Title")

After successful building we can login in in Docker and post our image to Docker Hub.