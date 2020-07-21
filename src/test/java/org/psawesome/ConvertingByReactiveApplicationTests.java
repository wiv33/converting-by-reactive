package org.psawesome;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.psawesome.kafka.KafkaManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverRecord;

import java.io.Serializable;
import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
class ConvertingByReactiveApplicationTests {

  @Test
  void testKafkaConnection() {
    ReceiverOffset offset = mock(ReceiverOffset.class);
    doNothing().when(offset).acknowledge();

    final ReceiverRecord<String, String> record = Mockito.mock(ReceiverRecord.class);
    when(record.key()).thenReturn(null);
    when(record.value()).thenReturn("psawesome");

    final KafkaManager manager = Mockito.mock(KafkaManager.class);
    Mockito.when(manager.producer(Mockito.any())).thenReturn(Flux.empty());
    Mockito.when(manager.consumer("ps-topic0")).thenReturn(Flux.just(record));
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
