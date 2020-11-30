package org.psawesome.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PsTemplateRepoTest {

  @Autowired
  PsTemplateRepo repo;

  @Autowired
  DatabaseClient databaseClient;

  @Test
  void testInit() {
    System.out.println("PsTemplateRepoTest.testInit");
    StepVerifier.create(repo.findAll())
            .recordWith(ArrayList::new)
            .consumeRecordedWith(s ->
                    s.forEach(d -> assertAll(
                            () -> assertNotNull(d.getTemplateId()),
                            () -> assertNotNull(d.getDescript()),
                            () -> assertNotNull(d.getSiteCd()),
                            () -> assertNotNull(d.getTemplateSeq()))
                    ))
            .thenCancel()
            .verify()
    ;
  }

  @Test
  void testFineOne() {
    databaseClient.select()
            .from(PsTemplate.class)
            .fetch()
            .first()
            .log()
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();
  }

/*
  @Test
  void testFineOneByRepo() {
    StepVerifier.create(repo.findOne(2).log())
            .assertNext(res -> assertAll(
                    () -> assertEquals(2, res.getTemplateSeq()),
                    () -> assertEquals(2, res.getTemplateSeq()),
                    () -> assertEquals(2, res.getTemplateSeq()),
                    () -> assertEquals(2, res.getTemplateSeq()),
                    () -> assertEquals(2, res.getTemplateSeq())
            ))
            .verifyComplete();
  }
*/
}