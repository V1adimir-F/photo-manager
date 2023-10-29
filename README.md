# Photo manager

Spring Boot Application for distributing photos into folders according to the date and place of creation

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
- [Maven 3](https://maven.apache.org)
- [Yandex geocoding API key](https://yandex.ru/dev/geocode/doc/ru/)

## Dependencies
There are a number of third-party dependencies used in the project. Browse the Maven pom.xml file for details of libraries and versions used.

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.github.photomanager.PhotoManagerApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Contributing

Please read [CONTRIBUTING.MD](https://github.com/V1adimir-F/photo-manager/blob/main/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/V1adimir-F/photo-manager/blob/main/LICENSE) file.
