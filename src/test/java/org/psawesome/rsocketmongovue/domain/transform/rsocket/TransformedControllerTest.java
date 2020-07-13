package org.psawesome.rsocketmongovue.domain.transform.rsocket;

import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.ServerTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.transport.netty.server.WebsocketServerTransport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransformedControllerTest {

  private static RSocketRequester requester;

  @BeforeAll
  static void beforeAll(@Autowired RSocketRequester.Builder builder, @Value("${spring.rsocket.server.port}") int port) {
    requester = builder
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .connectTcp("localhost", port)
            .block(Duration.ofSeconds(5));
  }

  @AfterAll
  static void afterAll() {
    Objects.requireNonNull(requester)
            .rsocket()
            .dispose();
  }

  @Test
  void testRequesterNotNull() {
    assertNotNull(requester);
    StepVerifier.create(requester.route("transformed.help")
            .retrieveFlux(String.class)
            .log())
            .expectNextCount(3)
            .verifyComplete();
  }

  @Test
  void test() {
  }

  // parameterized test

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