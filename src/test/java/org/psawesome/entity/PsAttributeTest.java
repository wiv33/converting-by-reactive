package org.psawesome.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PsAttributeTest {

  @Autowired
  DatabaseClient databaseClient;

  @Test
  void testFindAll() {
    final Flux<PsAttribute> selectAll = databaseClient.select()
            .from(PsAttribute.class)
            .fetch()
            .all()
            .log();

    StepVerifier.create(selectAll)
            .expectNextCount(5)
            .thenCancel()
            .verify()
    ;
  }
}