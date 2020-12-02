package org.psawesome.entity;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PsTemplateRepoTest extends CommonMockitoTest {

  @Test
  void testFineOne() {
    Hooks.onOperatorDebug();
    Hooks.onErrorDropped(throwable -> System.out.println(throwable.getMessage()));

    final DatabaseClient client = clientBuilder.build();
    final Statement statement = mockStatementFor("SELECT * FROM TTB_CMSC_TEMPLATE WHERE TEMPLATE_SEQ = $1");

    client.execute(() -> "SELECT * FROM TTB_CMSC_TEMPLATE WHERE TEMPLATE_SEQ = $1")
            .bind(0, 2)
            .then()
            .log()
            .as(StepVerifier::create)
            .verifyComplete();

    verify(statement).bind(0, 2);
    verify(statement).execute();

    verifyNoMoreInteractions(statement);
  }

  @Test
  void testItemShapeBySelect() {
    final PsTemplate template = Mockito.mock(PsTemplate.class);

    when(template.getTemplateId()).thenReturn("template3");
    when(template.getDescript()).thenReturn("템플릿 테스트 3");
    when(template.getSiteCd()).thenReturn("JAI");
  }

  @Test
  void testMockBeanIsNotNull() {
    Statement statement = mockStatementFor("SELECT * FROM TTB_CMSC_TEMPLATE WHERE TEMPLATE_SEQ = $1");

    final DatabaseClient client = clientBuilder.build();
    client.execute("SELECT * FROM TTB_CMSC_TEMPLATE WHERE TEMPLATE_SEQ = $1") //
            .bindNull(1, Integer.class) //
            .then() //
            .log()
            .as(StepVerifier::create) //
            .verifyComplete();

    verify(statement).bindNull(1, Integer.class);

    client.execute("SELECT * FROM TTB_CMSC_TEMPLATE WHERE TEMPLATE_SEQ = $1")
            .bindNull("$1", Integer.class)
            .then()
            .log()
            .as(StepVerifier::create)
            .verifyComplete();

    verify(statement).bindNull("$1", Integer.class);

  }

  @Test
  void executeShouldBindNameValuesFromIndexes() {
    Hooks.onOperatorDebug();
    Hooks.onErrorDropped(throwable -> System.out.println(throwable.getMessage()));
//    TODO issue gh-178, executeShouldBindNamedValuesFromIndexes,
//    https://github.com/spring-projects/spring-data-r2dbc/blob/master/src/test/java/org/springframework/data/r2dbc/core/DefaultDatabaseClientUnitTests.java
//    186L
//    522L
    Statement statement = mockStatementFor("SELECT * FROM TTB_CMSC_TEMPLATE WHERE TEMPLATE_ID IN ($n)");

    final DatabaseClient client = clientBuilder.build();

    client.execute("SELECT * FROM TTB_CMSC_TEMPLATE WHERE TEMPLATE_ID IN ($n)")
            .bind(0, Arrays.asList("template1", "template2", "template3"))
//            .bind(0, "template1")
//            .bind(1, "template2")
//            .bind(2, "template3")
            .then()
            .log()
            .as(StepVerifier::create)
            .verifyComplete();

    verify(statement).bind(0, Arrays.asList("template1", "template2", "template3"));
//    verify(statement).bind(1, "template2");
//    verify(statement).bind(2, "template3");
    verify(statement).execute();

    verifyNoMoreInteractions(statement);
  }

}