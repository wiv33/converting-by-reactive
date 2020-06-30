package org.psawesome.rsocketmongovue.api.http.router;

import lombok.RequiredArgsConstructor;
import org.psawesome.rsocketmongovue.api.http.handler.PsUserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 30. Tuesday
 */
@Component
@RequiredArgsConstructor
public class ApiRouter {

  private final PsUserHandler psUserHandler;

  @Bean
  public RouterFunction<ServerResponse> routerFunction() {
//    return RouterFunctions.nest(
//            GET("/").and(accept(MediaType.APPLICATION_JSON)),
//            req -> psUserHandler.userFindAll(req)
//    );
    return nest(path("/api/v1"),
            nest(accept(MediaType.APPLICATION_JSON),
                    route(GET("/user"),
                            psUserHandler::userFindAll
                    ))
//            .andRoute()
    );
  }

}
