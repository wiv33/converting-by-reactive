package org.psawesome.config;

import io.r2dbc.mssql.MssqlConnectionConfiguration;
import io.r2dbc.mssql.MssqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.dialect.SqlServerDialect;

@Configuration
@Slf4j
public class DatabaseConfig {

  @Value("${host}") String host;
  @Value("${user}") String user;
  @Value("${pass}") String pass;
  @Value("${db}") String db;

  @Bean
  DatabaseClient databaseClient() {
    return DatabaseClient.create(connectionFactory());
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    return new MssqlConnectionFactory(MssqlConnectionConfiguration.builder()
            .host(host)
            .port(1433)
            .database(db)
            .username(user)
            .password(pass)
            .build());
  }

  @Bean
  public DatabaseClient.Builder databaseClientBuilder() {
    return DatabaseClient.builder()
            .connectionFactory(connectionFactory())
            .dataAccessStrategy(new DefaultReactiveDataAccessStrategy(SqlServerDialect.INSTANCE));
  }
}
