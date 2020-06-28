package org.psawesome.rsocketmongovue.domain.user.entity.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PsUserResponse {
  private UUID uuid;
  private String email;
}
