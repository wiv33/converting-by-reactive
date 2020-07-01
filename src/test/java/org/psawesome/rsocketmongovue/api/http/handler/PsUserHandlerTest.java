package org.psawesome.rsocketmongovue.api.http.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.psawesome.rsocketmongovue.api.http.router.ApiRouter;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.res.PsUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 30. Tuesday
 */
@ExtendWith(SpringExtension.class)
@Import({
        ApiRouter.class,
        ReactiveMongoTemplate.class,
        PsUserHandler.class,
})
@WebFluxTest
class PsUserHandlerTest {

  @Autowired
  ApplicationContext context;

  @Autowired
  RouterFunction<?> userRouter;

  @Autowired
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