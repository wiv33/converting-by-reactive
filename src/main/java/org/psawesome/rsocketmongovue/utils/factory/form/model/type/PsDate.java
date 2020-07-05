package org.psawesome.rsocketmongovue.utils.factory.form.model.type;

import org.psawesome.rsocketmongovue.utils.factory.form.model.type.marker.PsValue;

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
public final class PsDate implements PsValue {
  private LocalDateTime value = LocalDateTime.now();

  @Override
  public <T> T getValue() {
    return (T) value;
  }

  @Override
  public <T> T setValue(T value) {
    this.value = (LocalDateTime) value;
    return ((T) this.value);
  }

}
