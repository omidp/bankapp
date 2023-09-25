
# Bank App

Bank App API

## Technologies

- Spring webmvc/jpa data/hibernate
- **[Maven](https://maven.apache.org/)** 
- Java 17
- Postgresql
- lombok
- Docker
- Swagger/OpenApi/Springfox
- Mockito

## Getting Started

### Requirements

- Docker
  - Needed in case you want to run it with Docker.
- Java 17
  - Need have the JAVA_HOME set in case you want to execute via Maven

### Building

At the project root folder, execute:

```shell
maven clean package
```


### Running


- Run the docker compose file

```bash
docker-compose -f "docker-compose.yml" up --build -d
```

- Run without docker 

* You need to have postgresql installed

```
java -jar target/bank-0.0.1-SNAPSHOT.jar
```


#### Swagger view

open url in the browser

```
http://localhost:8081/swagger-ui.html
```


### Troubleshoot

in case you got the below error 

```
10.5: Pulling from library/postgres
ERROR: toomanyrequests: You have reached your pull rate limit. You may increase the limit by authenticating and upgrading: https://www.docker.com/increase-rate-limit
```

login to your docker hub account 

```
docker login --username=
```

