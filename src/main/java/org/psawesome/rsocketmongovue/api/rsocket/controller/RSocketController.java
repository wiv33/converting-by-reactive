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

  @MessageMapping("psUser.all")
  public Flux<PsUserDto> psUserFindAll(){
    return reactiveMongoTemplate.findAll(PsUser.class)
            .map(s -> toDto.transfer(s, PsUserDto.class))
            .flatMap(Flux::from);
  }
}
