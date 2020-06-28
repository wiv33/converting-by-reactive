package org.psawesome.rsocketmongovue.domain.user.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

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
}
