package org.psawesome;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.util.Properties;

@SpringBootTest
class ConvertingByReactiveApplicationTests {

  @Test
  void testKafkaConnection() {
    final EmbeddedKafkaBroker test = new EmbeddedKafkaBroker(1, false, 1, "test");
    test.afterPropertiesSet();

  }
/*private static RSocketRequester requester;

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

*//*
  @Autowired
  ReactiveMongoRepository mongoRepository;
*//*

   *//*
    @Autowired
    ReactiveKafkaProducerTemplate kafkaProducerTemplate;
  *//*

   *//*
  @Autowired
  ReactiveKafkaConsumerTemplate kafkaConsumerTemplate;
*//*

  @Test
  void testContextLoads() {
    assertAll(
//            () -> assertNotNull(mongoRepository),
            () -> assertNotNull(operations),
            () -> assertNotNull(reactiveMongoTemplate)
    );
  }
*/
}
