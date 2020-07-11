package org.psawesome.rsocketmongovue.api.http.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.res.PsUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 30. Tuesday
 */
@SpringBootTest
class PsUserHandlerTest {

  @Autowired
  ApplicationContext context;

  @Autowired
  RouterFunction<?> userRouter;

  WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    webTestClient = WebTestClient.bindToRouterFunction(userRouter)
            .build();
  }

  @Test
  void testFindAll() {
    webTestClient.get()
            .uri("/api/v1/user")
            .header("X-USER-ID", "ps")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBody(PsUserResponse.class);
  }
}