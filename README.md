# Ok* testing

> Testing HTTP applications with _'a few ok libraries'_.


## Part 1: A type-safe HTTP client

How to run:

```bash
./gradlew check
```

Executes the tests and outputs a HTML report at ``build/reports/test/index.html``.


## Part 2: End-to-end testing a server-side application

How to run:
```bash
./gradlew check
```

Executes the tests and outputs HTML reports at ``build/reports/test/index.html`` and ``build/reports/e2eTest/index.html``.
The tests spawn a Spark application that is being tested.
Spark application connects to a mock backend and responds with some HTTP/JSON payload to the test client.


## Part 3: Build & deployment tooling

How to run:

```bash
docker-compose up --build -d
```

Builds a distributable Docker image for deployments.
Gradle build is executed and both tests and end-to-end tests are run when the Docker image is built.
