package org.psawesome.rsocketmongovue.domain.node.model;

import java.time.LocalDateTime;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 4. Saturday
 */
final class PsDate implements PsValue<LocalDateTime> {
  private LocalDateTime impl = LocalDateTime.now();

  @Override
  public PsDate getValue() {
    return this;
  }

  @Override
  public LocalDateTime getImpl() {
    return this.impl;
  }

  @Override
  public LocalDateTime setImpl(LocalDateTime impl) {
    return this.impl = impl;
  }
}
