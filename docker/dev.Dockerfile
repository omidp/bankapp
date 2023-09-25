FROM openjdk:17-oracle as builder
WORKDIR /usr/bank

COPY target/bank-0.0.1-SNAPSHOT.jar bank-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=target/bank-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} bank-0.0.1-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar bank-0.0.1-SNAPSHOT.jar extract

FROM openjdk:17-oracle
WORKDIR /usr/bank

RUN groupadd bank && useradd -g bank bank
USER bank

RUN mkdir -p /var/log

COPY --from=builder /usr/bank/dependencies/ ./
COPY --from=builder /usr/bank/spring-boot-loader/ ./
COPY --from=builder /usr/bank/snapshot-dependencies/ ./
COPY --from=builder /usr/bank/application/ ./

ENTRYPOINT ["java","-noverify", "org.springframework.boot.loader.JarLauncher"]
