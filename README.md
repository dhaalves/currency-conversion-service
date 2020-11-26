## Intro
Since this is a simple project, I choose a simple and slim architecture. This means I have only two visible layers on my project structure, the resource and the model layer.

The model layer uses Hibernate with Panache which brings nice support for using the Active Record pattern. For exposing the API I used JAX-RS spec.

You can check and try the API (doc generated using OPEN API specification) on https://app.swaggerhub.com/apis/dhaalves/generated-api/1.0
or http://localhost:8080/swagger-ui/#/, if you run locally;



## Stack

This project uses Quarkus, the Supersonic Subatomic Java Framework.
If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `currency-services-1.0-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/currency-services-1.0-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/currency-services-1.0-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.



