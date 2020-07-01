package org.psawesome.rsocketmongovue.api.rsocket.controller;

import lombok.RequiredArgsConstructor;
import org.psawesome.rsocketmongovue.domain.common.EntityToDto;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.PsUserDto;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 30. Tuesday
 */
@Controller
@RequiredArgsConstructor
public class RSocketController {

  private final EntityToDto toDto;

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  @MessageMapping("user.all")
  public Flux<PsUser> psUserFindAll(){
    return Flux.just(PsUser.builder().build())
            ;
  }

}
