package org.psawesome.rsocketmongovue.domain.user.rsocket;

import lombok.RequiredArgsConstructor;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 7. 2. Thursday
 */
@Controller
@RequiredArgsConstructor
public class PsUserController {

  private final ReactiveFluentMongoOperations operations;

  @MessageMapping("user.all")
  public Flux<PsUser> userFindAll() {
    return operations.query(PsUser.class)
            .all();
  }

  @MessageMapping("user.save")
  public Mono<PsUser> userSave(@Payload PsUser user) {
    return operations.insert(PsUser.class)
            .one(user)
            .log();
  }

  // 여기엔... transformed가능한 리스트 출력
  // 이 정도는 http에서 해도 되지 않을까?
  @MessageMapping("transformed.help")
  public Mono<?> transformedAll() {
    return Mono.just(List.of("TEXT", "JSON", "XML"))
            .log();
  }
}
