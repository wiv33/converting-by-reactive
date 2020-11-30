package org.psawesome.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PsChildTest {

  @Autowired
  DatabaseClient databaseClient;

  @Test
  void testFindAll() {
    final Flux<PsChild> log = databaseClient.select()
            .from(PsChild.class)
            .fetch()
            .all()
            .log();

    StepVerifier.create(log)
            .expectNextCount(3)
            .thenCancel()
            .verify();
  }
}