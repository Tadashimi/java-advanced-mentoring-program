If you don't have installed Kafka then firstly you can use docker-compose to run the container with Kafka.

There is a tool for schemas management called [Confluent](https://www.confluent.io/blog/apache-kafka-spring-boot-application/).

Send the Postman POST request on localhost:8080/msg with schemaId param and Body with message. schemaId can be found in the SchemaEnum.