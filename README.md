# Hack9 reference implementation

## Building application

Position yourself in the root of the project (directory where you found this
file) and run the following commands:

```shell
mvn clean package
```

### Building Docker image

If you wish to build a Docker image out of the project, either use Docker CLI or
run maven with docker profile.

```shell
# Build image
mvn -P docker package
# Push to AWS Docker repo
mvn -P docker install
```

Please note that, in order for the push to AWS ECR to work, you need to follow
these steps:

- Login to AWS console
- Generate credentials for AWS CLI
- Put credentials into `~/.aws/credentials`
- Run: `aws ecr get-login --no-include-email --region eu-west-1`
- Locate "username" and "password"
- Put username and password into `~/.mv/settings.xml` under section "servers"

```xml
<servers>
    <server>
        <id>745008152238.dkr.ecr.eu-west-1.amazonaws.com</id>
        <username>AWS</username>
        <password>...a.huge.pile.of.chars...</password>
    </server>
</servers>
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

### Running with Docker compose

If you have built your Docker images, you can run them using Docker Compose.
Position yourself in the root of the repository (parent project) and run:

```shell
docker-compose up
```

## Accessing application

Application is accessable on the base URL http://localhost:8080/reference/
For example on how to access it, see Postman JSON in `backend/src/test/scripts`.

## Stopping application

Just press `Ctrl+C` (in both windows where you started reference and judge thread).
Or, if you were using Docker Compose, just press Ctrl+C in the main window or use
`docker-compose stop` from another terminal.
