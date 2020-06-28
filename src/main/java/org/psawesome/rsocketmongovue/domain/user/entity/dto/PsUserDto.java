package org.psawesome.rsocketmongovue.domain.user.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;

import java.util.UUID;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
@Data
@NoArgsConstructor
@Builder
public class PsUserDto {
  private UUID uuid;
  private String name;
  private String phone;
  private String email;
  private Integer age;

  public PsUserDto(UUID uuid, String name, String phone, String email, Integer age) {
    this.uuid = uuid;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.age = age;
  }

  public PsUserDto transform(PsUser entity) {
    PsUserDto result = new PsUserDto();
//    return Flux.fromStream(Stream.of(result.getClass().getDeclaredFields()))
//            .map(item -> {
//              Field declaredField = entity.getClass().getDeclaredField(item.getName());
//            })
//            .map(field -> )
    ;
    return result;
  }
}
