package org.psawesome.rsocketmongovue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "application.properties"
        })
//@EnableKafka
@EnableReactiveMongoRepositories
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
    PsUser user = PsUser.builder()
            .name("ps")
            .phone("010-0000-0000")
            .email("psk@gmail.com")
            .build();

    Publisher<PsUser> save = reactiveMongoTemplate.<PsUser>save(user);
    StepVerifier.<PsUser>create(save)
            .expectNextMatches(o -> o.equals(user))
            .assertNext(psUser ->
                    assertAll(
                            () -> assertEquals("ps", psUser.getName()),
                            () -> assertEquals("010-0000-0000", psUser.getPhone()),
                            () -> assertEquals("psk@gmail.com", psUser.getEmail()))
            )
            .expectComplete()
            .verify()
    ;

  }
}
