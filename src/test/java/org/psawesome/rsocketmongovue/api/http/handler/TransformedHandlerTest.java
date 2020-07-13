package org.psawesome.rsocketmongovue.api.http.handler;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
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
class TransformedHandlerTest {

  private RSocketRequester requester;
/*
  @BeforeAll
  static void beforeAll(@Autowired RSocketRequester.Builder builder, @Value("${spring.rsocket.server.port}") int port) {
    requester = builder.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .connectTcp("localhost", port)
            .block(Duration.ofSeconds(3));
  }
*/

/*
  @AfterAll
  static void afterAll() {
    requester.rsocket().dispose();
  }
*/

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
    requester.route("transformed.create")
            .data(TransformedRequest.builder().build())
    ;
  }

  @ParameterizedTest
  @MethodSource(value = "arguments")
  void testTcpServer(ServerTransport<CloseableChannel> serverTransport) {
    Mono<CloseableChannel> server;
    server = RSocketServer.create(mockAcceptor())
            .bind(serverTransport)
            .doOnNext(CloseableChannel::dispose);
    StepVerifier.create(server)
            .expectNextCount(1)
            .verifyComplete();
  }

  @ParameterizedTest
  @MethodSource("arguments")
  void clientSucceedsWithDisabledFragmentation(ServerTransport<CloseableChannel> serverTransport) {
    CloseableChannel server = RSocketServer.create(mockAcceptor()).bind(serverTransport).block();

    Mono<RSocket> rSocket = RSocketConnector.connectWith(TcpClientTransport.create(server.address()))
            .doFinally(s -> server.dispose());

    StepVerifier.create(rSocket).expectNextCount(1).expectComplete().verify(Duration.ofSeconds(5));
  }

  static Stream<? extends ServerTransport<CloseableChannel>> arguments() {
    return Stream.of(TcpServerTransport.create(0), WebsocketServerTransport.create(0));
  }

  private SocketAcceptor mockAcceptor() {
    SocketAcceptor mock = Mockito.mock(SocketAcceptor.class);
    Mockito.when(mock.accept(Mockito.any(), Mockito.any()))
            .thenReturn(Mono.just(Mockito.mock(RSocket.class)));
    return mock;
  }
}