package org.psawesome.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.dto.request.XmlRequest;
import org.psawesome.router.XmlRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
  Mono<List<Map<String, Object>>> source;

  @BeforeEach
  void setUp() throws IOException {
    testClient = WebTestClient.bindToRouterFunction(xmlRouter.xmlRouter())
            .build();
    final ObjectMapper mapper = new ObjectMapper();
    body = mapper.readValue(Files.readString(Paths.get("input-one-depth.json")),
            new TypeReference<>() {
            });
    source = Flux.fromStream(Files.lines(Paths.get("input-one-depth.json")))
            .reduce(new StringBuilder(), StringBuilder::append)
            .map(s -> {
              try {
                return mapper.readValue(s.toString(), new TypeReference<>() {
                });
              } catch (JsonProcessingException e) {
                throw new RuntimeException();
              }
            });
  }

  @Test
  void testHandler() {
    final XmlRequest psawesome = XmlRequest.builder()
            .id("psawesome")
            .body(body)
            .build();
    final Disposable subscribe = source
            .map(maps -> XmlRequest
                    .builder()
                    .body(maps)
                    .build())
            .publishOn(Schedulers.single())
            .log()
            .subscribe();
    Assertions.assertNotNull(psawesome.getBody());
    subscribe.dispose();
    Assertions.assertTrue(subscribe.isDisposed());
  }
}
