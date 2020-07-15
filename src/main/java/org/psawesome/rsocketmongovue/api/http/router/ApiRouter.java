package org.psawesome.rsocketmongovue.api.http.router;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.psawesome.rsocketmongovue.api.http.handler.PsUserHandler;
import org.psawesome.rsocketmongovue.api.http.handler.TransformedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 30. Tuesday
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApiRouter {

  private final PsUserHandler psUserHandler;
  private final TransformedHandler transformedHandler;

  @Bean
  public RouterFunction<?> userRouter() {
    return route().path("/api/v1/user", b1 -> b1
            .nest(accept(MediaType.APPLICATION_JSON), b2 -> b2
                    .GET("/{id}", psUserHandler::findUser)
                    .GET("", psUserHandler::userFindAll)
                    .before(request -> ServerRequest.from(request)
                            .header("X-USER-ID", request.headers().firstHeader("X-USER-ID")).build()))
            .POST("/user", psUserHandler::save))
            .build();
  }

  @Bean
  @CrossOrigin(origins = {"http://psawesome.org"}, maxAge = -1)
  public RouterFunction<?> transformedRouter() {
    return route().path("/api/v1/transformed", b1 -> b1
                    .nest(accept(MediaType.APPLICATION_JSON), b2 -> b2
                            .GET("/type/{type}/", transformedHandler::create)
                            .POST("", transformedHandler::create)
                            .before(request -> ServerRequest.from(request)
                                    .header("X-USER-ID", "temp")
                                    .build())
                    )
                    .nest(accept(MediaType.TEXT_EVENT_STREAM), b2 -> b2
                            .GET("qqq", transformedHandler::testQQQ)
                            .GET("www", transformedHandler::testWWW)
                            .GET("eee", transformedHandler::testEEE)
                    )

//            .POST("/xml", psUserHandler::userFindAll)
    )
            .after(((request, response) -> (response)))
            .build();
  }
}