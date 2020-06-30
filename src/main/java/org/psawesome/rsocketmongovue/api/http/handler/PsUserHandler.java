package org.psawesome.rsocketmongovue.api.http.handler;

import lombok.RequiredArgsConstructor;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.PsUserDto;
import org.springframework.messaging.rsocket.RSocketRequester;
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

  private final Mono<RSocketRequester> requesterMono;

  public Mono<ServerResponse> userFindAll(ServerRequest request) {
    String userInfo = Objects.requireNonNull(request.headers()
            .firstHeader("X-USER-ID"));

    return ok().body(
            requesterMono.<Object>map(rRequest -> rRequest.route("psUser").retrieveFlux(PsUserDto.class)),
            PsUser.class)
            .log()
            ;
  }
}
