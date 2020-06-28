package org.psawesome.rsocketmongovue.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("PS_USER")
public class PsUser {

  @Id
  private UUID uuid;

  private String name;
  private String phone;
  private String email;

  @Builder
  public PsUser(String name, String phone, String email) {
    this.uuid = UUID.randomUUID();
    this.name = name;
    this.phone = phone;
    this.email = email;
  }
}
