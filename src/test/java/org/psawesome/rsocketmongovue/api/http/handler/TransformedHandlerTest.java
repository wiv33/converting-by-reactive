package org.psawesome.rsocketmongovue.api.http.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.ServerTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.transport.netty.server.WebsocketServerTransport;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.psawesome.rsocketmongovue.domain.transform.dto.request.TransformedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.rsocket.server.RSocketServerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

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
//@ExtendWith(MockitoExtension.class)
class TransformedHandlerTest {

  private static RSocketRequester requester;
  @BeforeAll
  static void beforeAll(@Autowired RSocketRequester.Builder builder, @Value("${spring.rsocket.server.port}") int port) {
    requester = builder.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .connectTcp("localhost", port)
            .block(Duration.ofSeconds(3));
  }

  @AfterAll
  static void afterAll() {
    requester.rsocket().dispose();
  }

  @AfterEach
  void tearDown() {

  }


  @BeforeEach
  void setUp(@Autowired RSocketRequester.Builder builder, @Value("${spring.rsocket.server.port}") int port) {
    requester = builder.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .connectTcp("localhost", port)
            .block(Duration.ofSeconds(3));
  }

  @Test
  void testWebTestClientNotNull() {
    StepVerifier.create(requester.route("transformed.help")
            .retrieveMono(String.class)
    .log())
            .expectNextCount(4)
            .verifyComplete()
    ;
  }

}