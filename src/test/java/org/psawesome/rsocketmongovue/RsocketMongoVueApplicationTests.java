package org.psawesome.rsocketmongovue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.PsUserDto;
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

    Publisher<PsUser> save = reactiveMongoTemplate.save(user);
    PsUserDto expected = PsUserDto.builder()
            .name("ps")
            .email("psk@gmail.com")
            .phone("010-0000-0000")
            .build();
    StepVerifier.create(save).consumeNextWith(response ->
            assertAll(
                    () -> assertEquals(response, user),
                    () -> assertEquals(expected.getName(), response.getName()),
                    () -> assertEquals(expected.getPhone(), response.getPhone()),
                    () -> assertEquals(expected.getEmail(), response.getEmail()),
                    () -> assertThrows(NullPointerException.class, () -> System.out.println("failTest = " + "failTest"))
            )
    )
            .expectComplete()
            .verify()
    ;

  }
}
