package org.psawesome.rsocketmongovue.domain.transform.rsocket;

import lombok.RequiredArgsConstructor;
import org.psawesome.rsocketmongovue.domain.transform.dto.request.TransformedRequest;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

/**
 * package: org.psawesome.rsocketmongovue.domain.transform.rsocket
 * author: PS
 * DATE: 2020-07-12 일요일 04:57
 */
@Controller
@RequiredArgsConstructor
public class TransformedController {

  private final ReactiveFluentMongoOperations fluentMongoOperations;

  // 여기엔... transformed가능한 리스트 출력
  // 이 정도는 http에서 해도 되지 않을까?
  @MessageMapping("transformed.help")
  public Flux<String> transformedHelp() {
    return Flux.fromStream(Stream.of("TEXT", "JSON", "XML"))
            .log();
  }

  @MessageMapping("transformed.request")
  public Mono<TransformedRequest> transformedRequest(@Payload TransformedRequest request) {
    return fluentMongoOperations.insert(TransformedRequest.class)
            .one(request);
  }

}
