package org.psawesome.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.dto.request.XmlRequest;
import org.psawesome.kafka.KafkaManager;
import org.psawesome.router.XmlRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author ps [https://github.com/wiv33/converting-by-reactive]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 8. 3. Monday
 */
//@ExtendWith(SpringExtension.class)
@WebFluxTest
@ImportAutoConfiguration(classes = {XmlHandler.class, XmlRouter.class})
//@AutoConfigureWebTestClient
//@AutoConfigureWebFlux
public class XmlHandlerTest {

  private WebTestClient testClient;

  @Autowired
  XmlRouter xmlRouter;

  @Autowired
  XmlHandler handler;

  List<Map<String, Object>> body;

  final KafkaManager manager = new KafkaManager(getTopic());
  Flux<ReceiverRecord<String, String>> consumer;

  private String getTopic() {
    return "xml-request";
  }

  @BeforeEach
  void setUp() throws IOException {
    testClient = WebTestClient.bindToRouterFunction(xmlRouter.xmlRouter())
            .build();
    final ObjectMapper mapper = new ObjectMapper();
    body = mapper.readValue(Files.readString(Paths.get("input-one-depth.json")),
            new TypeReference<>() {
            }
    );

    consumer = manager.consumer(getTopic());
  }

  @Test
  void testHandler() {
    final XmlRequest psawesome = XmlRequest.builder()
            .id("psawesome")
            .body(body)
            .build();
    Assertions.assertNotNull(psawesome.getBody());
  }

  @Test
  void testConsumer() throws InterruptedException, IOException {
    final Flux<Long> interval = Flux.interval(Duration.ofMillis(700));
    Flux.zip(interval, sendMessageInterval().delayElements(Duration.ofMillis(333)).repeat(7))
            .log("interval !!! ")
            .subscribe(s -> System.out.println(s.getT2().recordMetadata().toString()),
                    throwable -> System.out.println(throwable.getMessage()),
                    () -> System.out.println("onComplete"));

    System.out.println("XmlHandlerTest.testConsumer");
    consumer.elapsed()
            .log()
            .subscribe(s -> System.out.println(s.getT2().value()),
                    throwable -> System.out.println(throwable.getMessage()),
                    () -> System.out.println("my - onComplete")
            );

    StepVerifier.create(consumer.log("my-body-is"))
            .expectSubscription()
            .consumeNextWith(consume ->
                    Assertions.assertNotNull(consume.toString())
            )
    ;

    Thread.sleep(10_000);
  }

  private Flux<SenderResult<String>> sendMessageInterval() throws IOException {
    return Flux.fromStream(Files.lines(Paths.get("input-one-depth.json")))
            .publishOn(Schedulers.single())
            .reduce(new StringBuilder(), StringBuilder::append)
            .log("result string-builder")
            .flatMapMany(str -> manager.producer(
                    Mono.just(
//                            SenderRecord.create(new ProducerRecord<>(getTopic(), str.toString()), "")
                            SenderRecord.create(getTopic(), 0, Instant.now().toEpochMilli(), null, str.toString(), "")
                    )
            ))
            .metrics()
            .log("sent producer")
            ;
  }
}
