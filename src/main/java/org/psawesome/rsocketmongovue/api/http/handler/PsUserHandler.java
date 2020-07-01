package org.psawesome.rsocketmongovue.api.http.handler;

import lombok.RequiredArgsConstructor;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 30. Tuesday
 */
@Component
@RequiredArgsConstructor
public class PsUserHandler {

//  private final RSocketRequester requester;

  public Mono<ServerResponse> userFindAll(ServerRequest request) {
    String userInfo = Objects.requireNonNull(request.headers()
            .firstHeader("X-USER-ID"));

//    Flux<PsUser> publisher = requester.route("user.all").retrieveFlux(PsUser.class);
//    publisher.log();
//    publisher.subscribe(System.out::println);

    return ok().body(
            Mono.just(""),
            PsUser.class)
            .log()
            ;
  }

  public Mono<ServerResponse> findUser(ServerRequest request) {
    return null;
  }

  public Mono<ServerResponse> save(ServerRequest request) {
    return null;
  }
}
