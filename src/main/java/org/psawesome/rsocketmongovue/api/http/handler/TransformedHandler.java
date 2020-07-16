package org.psawesome.rsocketmongovue.api.http.handler;

import lombok.RequiredArgsConstructor;
import org.psawesome.rsocketmongovue.domain.transform.dto.request.TransformedRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

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

  public Mono<ServerResponse> testWWW(ServerRequest request) {
    return getMethod("WWW", 700L);
  }

  public Mono<ServerResponse> testQQQ(ServerRequest request) {
    return getMethod("QQQ", 1700L);
  }

  public Mono<ServerResponse> testEEE(ServerRequest request) {
    return getMethod("EEE", 333L);
  }



  private Mono<ServerResponse> getMethod(String www, Long time) {
    return ok().contentType(MediaType.TEXT_EVENT_STREAM)
            .body(getCurrentMethod(www, time), String.class);
  }

  private Flux<String> getCurrentMethod(String name, Long time) {
    return Flux.interval(Duration.ofMillis(time))
            .zipWith(Flux.generate((sink) -> sink.next(String.format("method name is %s, number - %d", name, ThreadLocalRandom.current().nextInt()))))
            .log("tuple is --> ")
            .map(Tuple2::toString);

  }
}
