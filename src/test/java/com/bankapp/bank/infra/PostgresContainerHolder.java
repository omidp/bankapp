package com.bankapp.bank.infra;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainerHolder {


	private final PostgreSQLProperties properties;
	private final PostgreSQLContainer postgresql;

	public PostgresContainerHolder(PostgreSQLProperties properties) {
		this.properties = properties;
		this.postgresql = new PostgreSQLContainer<>(properties.getDefaultDockerImage())
			.withUsername(properties.getUser())
			.withPassword(properties.getPassword())
			.withDatabaseName(properties.getDatabase())
			.withInitScript(properties.getInitScriptPath());
		this.postgresql.start();
	}

	public PostgreSQLContainer getContainer() {
		return postgresql;
	}

	public PostgreSQLProperties getProperties() {
		return properties;
	}
}