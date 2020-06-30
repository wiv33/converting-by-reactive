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
  private String uuid;

  private String name;
  private String phone;
  private String email;
  private Integer age;

  @Builder
  public PsUser(String name, String phone, String email, Integer age) {
    this.uuid = UUID.randomUUID().toString();
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.age = age;
  }
}
