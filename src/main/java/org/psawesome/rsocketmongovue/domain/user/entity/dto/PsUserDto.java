package org.psawesome.rsocketmongovue.domain.user.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.stream.Stream;

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

  public Mono<PsUserDto> transform(PsUser entity) {
    return Flux.fromStream(Stream.of(PsUserDto.class.getDeclaredFields()))
            .reduce(PsUserDto.builder().build(), (psUserDto, field) -> {
              try {
                Field entityField = entity.getClass().getDeclaredField(field.getName());
                entityField.setAccessible(true);
                Field dtoField = psUserDto.getClass().getDeclaredField(field.getName());
                dtoField.setAccessible(true);
                dtoField.set(psUserDto, entityField.get(entity));
                return psUserDto;
              } catch (NoSuchFieldException | IllegalAccessException e) {
                return psUserDto;
              }
            });
  }
}
