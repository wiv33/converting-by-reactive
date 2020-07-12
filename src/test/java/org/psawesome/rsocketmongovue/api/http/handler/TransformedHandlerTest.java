package org.psawesome.rsocketmongovue.api.http.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.server.RouterFunction;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 12. Sunday
 */
@SpringBootTest
class TransformedHandlerTest {

  @Autowired
  RouterFunction<?> transformedRouter;

  WebTestClient client;

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


  @BeforeEach
  void setUp() {
    client = WebTestClient.bindToRouterFunction(transformedRouter)
            .build();
  }

  @Test
  void testWebTestClientNotNull() {
    assertNotNull(client);
  }


}