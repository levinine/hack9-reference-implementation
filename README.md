# Hack9 reference implementation

## Building

Position yourself in the root of the project (directory where you found this
file) and run the following commands:

```shell
mvn clean package
```

## Running the application

Application consists of two components: the reference implementation and
the optional Skeleton Judge Thread. Skeleton Judge Thread hosts the
callback for invoice generation report. If you have anything else that can
simulate it, as defined by OpenAPI YMLs, feel free to do so.

### Running reference implementation

To run the application, position in "backend" forlder and from there rung
Spring Boot application:

```shell
cd backend
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Running Skeleton Judge Thread

From the root of the project position into the backend folder of the skeleton
Judge Thread and run it.

```shell
cd control/backend
java -jar target/control-backend-0.0.1-SNAPSHOT.jar
```

## Accessing application

Application is accessable on the base URL http://localhost:8080/reference/
For example on how to access it, see Postman JSON in `backend/src/test/scripts`.

## Stopping application

Just press `Ctrl+C` (in both windows where you started reference and judge thread).
