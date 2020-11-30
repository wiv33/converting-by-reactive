package org.psawesome.entity;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.springframework.data.relational.core.query.Criteria.where;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PsAttributeTest {

  @Autowired
  DatabaseClient databaseClient;

  @AfterAll
  void afterAll() {
    final Mono<Integer> log = databaseClient.delete()
            .from(PsAttribute.class)
            .matching(where("attribute_key").is("ps_is"))
            .fetch()
            .rowsUpdated()
            .log();
    StepVerifier.create(log)
            .thenCancel()
            .verify()
    ;
  }

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

  @DisplayName("조회하기")
  @ParameterizedTest
  @ValueSource(strings = {"psawesome", "mybodyis", "numerics is"})
  @Rollback
  void testFindOne(String attribute_value) {
    final Mono<Integer> insert = databaseClient.insert()
            .into(PsAttribute.class)
            .table("TTB_CMSC_TEMPLATE_ATTRIBUTE")
            .using(PsAttribute.builder()
                    .siteCd("JAI")
                    .childSeq("7")
                    .attributeKey("ps_is")
                    .attributeValue(attribute_value)
                    .attributeRemark("")
                    .modSeq("1925")
                    .modDt(LocalDateTime.now())
                    .regSeq("1925")
                    .regDt(LocalDateTime.now())
                    .build())
            .fetch()
            .rowsUpdated();

    StepVerifier.create(insert)
            .expectNextCount(1)
            .verifyComplete()
    ;


    final Mono<PsAttribute> attr = databaseClient.select()
            .from(PsAttribute.class)
            .matching(where("attribute_value").is(attribute_value))
            .fetch()
            .first()
            .log();

    StepVerifier.create(attr)
            .expectNextCount(1)
            .verifyComplete();
  }


}