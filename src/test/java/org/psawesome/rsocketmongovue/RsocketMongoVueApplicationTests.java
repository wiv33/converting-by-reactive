package org.psawesome.rsocketmongovue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "application.properties"
        })
@EnableKafka
@EnableReactiveMongoRepositories
@DataJpaTest
class RsocketMongoVueApplicationTests {

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  @Autowired
  ReactiveMongoOperations operations;

/*
  @Autowired
  ReactiveMongoRepository mongoRepository;
*/

  /*
    @Autowired
    ReactiveKafkaProducerTemplate kafkaProducerTemplate;
  */

/*
  @Autowired
  ReactiveKafkaConsumerTemplate kafkaConsumerTemplate;
*/

  @Test
  void testContextLoads() {
    assertAll(
//            () -> assertNotNull(mongoRepository),
            () -> assertNotNull(operations),
            () -> assertNotNull(reactiveMongoTemplate)
    );
  }


  @Test
  @DisplayName("setUp 시 save 확인")
  void testSetUpSaveMongo() {
    PsUser user = new PsUser().builder()
            .name("ps")
            .phone("010-0000-0000")
            .email("psk@gmail.com")
            .build();

    Publisher<PsUser> save = reactiveMongoTemplate.<PsUser>save(user);
    StepVerifier.<PsUser>create(save)
            .expectNextMatches(o -> o.equals(user))
            .assertNext(psUser ->
            assertAll(
                    () ->psUser.name().equals("ps"),
                    () -> psUser.phone().equals("010-0000-0000"),
                    () -> psUser.email().equals("psk@gmail.com")
            ))
    .expectComplete()
    .verify()
    ;

  }
}
