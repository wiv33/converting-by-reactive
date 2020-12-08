package org.psawesome.entity;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.dialect.SqlServerDialect;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PsChildTest {

  @Mock
  Connection connection;

  DatabaseClient.Builder clientBuilder;

  @BeforeEach
  void setUp() {
    final ConnectionFactory factory = Mockito.mock(ConnectionFactory.class);

    when(factory.create()).thenReturn((Publisher) Mono.just(connection));
    when(connection.close()).thenReturn(Mono.empty());

    clientBuilder = DatabaseClient.builder()
            .connectionFactory(factory)
            .dataAccessStrategy(new DefaultReactiveDataAccessStrategy(SqlServerDialect.INSTANCE));
  }

  @Test
  void shouldBeEqualStatement() {
    final Statement statement = Mockito.mock(Statement.class);
    var SQL = "SELECT * FROM TTB_CMSC_TEMPLATE_CHILD";

    when(connection.createStatement(SQL == null ? anyString() : eq(SQL))).thenReturn(statement);

    when(statement.returnGeneratedValues(anyString())).thenReturn(statement);
    when(statement.returnGeneratedValues()).thenReturn(statement);

    Mockito.doReturn(Mono.empty()).when(statement).execute();

    final DatabaseClient client = clientBuilder.build();
    client.execute(SQL)
            .fetch()
            .all()
            .then().log()
            .as(StepVerifier::create)
            .verifyComplete();

    verify(statement).execute();
    verifyNoMoreInteractions(statement);
  }

  @Test
  @DisplayName("두 개의 Query가 같지 않을 경우 statement Null 에러 발생")
  void shouldOccurredNullPointerException() {
    final Statement statement = mock(Statement.class);
    var SQL = "SELECT * FROM TTB_CMSC_TEMPLATE_CHILD WHERE TEMPLATE_SEQ = $1";
    when(connection.createStatement(SQL)).thenReturn(statement);
    when(statement.returnGeneratedValues(anyString())).thenReturn(statement);
    when(statement.returnGeneratedValues()).thenReturn(statement);

    final DatabaseClient client = clientBuilder.build();
    client.execute("SELECT * FROM TTB_CMSC_TEMPLATE_CHILD WHERE TEMPLATE-SEQ = :key")
            .bind("key", 3)
            .fetch()
            .one()
            .as(StepVerifier::create)
            .expectError(NullPointerException.class)
            .verify();

    verifyNoMoreInteractions(statement);
  }

  @Test
  void executeShouldBindNameNullValues() {
    final Statement statement = mock(Statement.class);
    var SQL = "SELECT * FROM TTB_CMSC_TEMPLATE_CHILD WHERE TEMPLATE_SEQ = $1";
    when(connection.createStatement(eq(SQL))).thenReturn(statement);
    when(statement.returnGeneratedValues(anyString())).thenReturn(statement);
    when(statement.returnGeneratedValues()).thenReturn(statement);

    Mockito.doReturn(Mono.empty()).when(statement).execute();

    final DatabaseClient client = clientBuilder.build();
    client.execute(() -> "SELECT * FROM TTB_CMSC_TEMPLATE_CHILD WHERE TEMPLATE_SEQ = $1")
            .bindNull(0, Integer.class)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();

    verify(statement).bindNull(0, Integer.class);
    verify(statement).execute();
    verifyNoMoreInteractions(statement);
  }

}