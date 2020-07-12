package org.psawesome.rsocketmongovue.api.http.handler;

import lombok.RequiredArgsConstructor;
import org.psawesome.rsocketmongovue.domain.transform.dto.request.TransformedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 11. Saturday
 */
@Component
@RequiredArgsConstructor
public class TransformedHandler {

//  private final RSocketRequester requester;

  public Mono<ServerResponse> create(ServerRequest request) {
    Mono<TransformedRequest> data = request.bodyToMono(TransformedRequest.class);
/*
    Mono<TransformedRequest> publisher = requester.route("transformed.create")
            .data(data)
            .retrieveMono(TransformedRequest.class)
            .log();
*/

    return ok().body(data,
            TransformedRequest.class)
            .switchIfEmpty(notFound().build());
  }
}
