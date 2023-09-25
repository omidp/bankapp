package com.bankapp.bank.infra;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PostgreSQLProperties  {
    static final String BEAN_NAME_EMBEDDED_POSTGRESQL = "embeddedPostgreSql";

    String user = "bank";
    String password = "bank";
    String database = "bankdb";
    String startupLogCheckRegex;
    /**
     * The SQL file path to execute after the container starts to initialize the database.
     */
    String initScriptPath;

    // https://hub.docker.com/_/postgres
    public String getDefaultDockerImage() {
        return "postgres:14-alpine";
    }
}