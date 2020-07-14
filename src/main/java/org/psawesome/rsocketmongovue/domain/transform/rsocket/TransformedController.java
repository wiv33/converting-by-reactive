package org.psawesome.rsocketmongovue.domain.transform.rsocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.psawesome.rsocketmongovue.domain.transform.dto.request.TransformedRequest;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * package: org.psawesome.rsocketmongovue.domain.transform.rsocket
 * author: PS
 * DATE: 2020-07-12 일요일 04:57
 */
@Slf4j
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

  @MessageMapping("transformed.findOne.{id}")
  public Mono<TransformedRequest> transformedRequestMono(@DestinationVariable String id) {
    log.info("request id is : {}", id);
    return fluentMongoOperations.query(TransformedRequest.class)
            .matching(Query.query(where("id").is(id)))
            .one();
  }

}
