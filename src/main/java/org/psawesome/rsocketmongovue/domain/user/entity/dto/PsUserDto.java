package org.psawesome.rsocketmongovue.domain.user.entity.dto;

import lombok.Builder;
import lombok.Data;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import reactor.core.publisher.Flux;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
@Data
@Builder
public class PsUserDto {
  private UUID uuid;
  private String name;
  private String phone;
  private String email;

  public PsUserDto(UUID uuid, String name, String phone, String email) {
    this.uuid = uuid;
    this.name = name;
    this.phone = phone;
    this.email = email;
  }

  public PsUserDto transform(PsUser entity) {
    Flux.fromStream(Stream.of(PsUser.class.getDeclaredFields()))
    ;
  }
}
