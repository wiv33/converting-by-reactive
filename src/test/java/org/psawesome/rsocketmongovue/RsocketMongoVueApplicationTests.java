package org.psawesome.rsocketmongovue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableKafka
@EnableReactiveMongoRepositories
class RsocketMongoVueApplicationTests {

  private static RSocketRequester requester;

  @BeforeAll
  static void beforeAll(@Autowired RSocketRequester.Builder builder, @Value("${spring.rsocket.server.port}") int port) {
    requester = builder.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .connectTcp("localhost", port)
            .block(Duration.ofSeconds(3));
  }

  @AfterEach
  void tearDown() {
    requester.rsocket().dispose();
  }


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
