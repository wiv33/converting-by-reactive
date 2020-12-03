package org.psawesome.entity;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.dialect.SqlServerDialect;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CommonMockitoTest {

  @Mock
  Connection connection;

  DatabaseClient.Builder clientBuilder;

  @BeforeEach
  void setUp() {
//    Mocking ConnectionFactory
    ConnectionFactory connectionFactory = Mockito.mock(ConnectionFactory.class);

    when(connectionFactory.create()).thenReturn((Publisher) Mono.just(connection));
    when(connection.close()).thenReturn(Mono.empty());
    clientBuilder = DatabaseClient.builder()
            .connectionFactory(connectionFactory)
            .dataAccessStrategy(new DefaultReactiveDataAccessStrategy(SqlServerDialect.INSTANCE));
  }

  @AfterEach
  void tearDown() {
    clientBuilder = null;
  }

  Statement mockStatement() {
    return mockStatementFor(null, null);
  }

  Statement mockStatement(Result result) {
    return mockStatementFor(null, result);
  }

  /**
   * @param sql
   * @return
   */
  Statement mockStatementFor(String sql) {
    return mockStatementFor(sql, null);
  }

  /**
   * SQL Query를 입력받아
   *
   * @param sql
   * @param result
   * @return
   */
  Statement mockStatementFor(@Nullable String sql, @Nullable Result result) {

    Statement statement = mock(Statement.class);
//    when(connection.createStatement(anyString())).thenReturn(statement);
    when(connection.createStatement(sql == null ? anyString() : eq(sql))).thenReturn(statement);
    when(statement.returnGeneratedValues(anyString())).thenReturn(statement);
    when(statement.returnGeneratedValues()).thenReturn(statement);

    doReturn(result == null ? Mono.empty() : Flux.just(result)).when(statement).execute();

    return statement;
  }
}
