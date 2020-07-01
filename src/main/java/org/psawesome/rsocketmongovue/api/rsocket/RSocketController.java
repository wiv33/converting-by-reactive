package org.psawesome.rsocketmongovue.api.rsocket;

import lombok.AllArgsConstructor;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 7. 2. Thursday
 */
@Controller
@AllArgsConstructor
public class RSocketController {

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
}
