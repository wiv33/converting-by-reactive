package org.psawesome.rsocketmongovue.api.rsocket.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 30. Tuesday
 */
@SpringBootTest
class RSocketControllerTest {

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

  @Test
  void testRSocketConnection() {

    PsUser build = PsUser.builder()
            .name("ps")
            .phone("010")
            .email("psk@gmail.com")
            .age(17)
            .build();

    StepVerifier.create(requester.route("user.save")
            .data(build)
            .retrieveMono(PsUser.class))
            .assertNext(consume -> assertAll(
                    () -> assertEquals("ps", consume.getName()),
                    () -> assertEquals("010", consume.getPhone()),
                    () -> assertEquals("psk@gmail.com", consume.getEmail()),
                    () -> assertEquals(17, consume.getAge()),
                    () -> assertEquals(build.getUuid(), consume.getUuid())
            ))
            .verifyComplete()
    ;

    StepVerifier.create(requester.route("user.all")
            .retrieveFlux(PsUser.class)
    .log("user.all -->>> "))
            .expectNextCount(1)
            .verifyComplete();
  }
}