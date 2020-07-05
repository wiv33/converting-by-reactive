package org.psawesome.rsocketmongovue.utils.factory.form.model.type.impl;

import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsValue;

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
public final class PsDate implements PsValue<LocalDateTime> {
  private LocalDateTime value = LocalDateTime.now();

  @Override
  public PsDate getValue() {
    return this;
  }

  @Override
  public LocalDateTime getImpl() {
    return this.value;
  }

  @Override
  public LocalDateTime setImpl(LocalDateTime value) {
    return this.value = value;
  }
}
