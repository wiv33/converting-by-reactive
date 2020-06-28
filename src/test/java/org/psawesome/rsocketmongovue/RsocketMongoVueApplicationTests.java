package org.psawesome.rsocketmongovue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "application.yml"
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

}
