package org.psawesome.rsocketmongovue.utils.factory.form.model.type;

import org.psawesome.rsocketmongovue.utils.factory.form.model.type.marker.PsValue;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role String (단일) 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */
public final class PsString implements PsValue {

  private String value = "";

  @Override
  public <T> T getValue() {
    return (T) value;
  }

  @Override
  public <T> T setValue(T value) {
    this.value = (String)value;
    return (T) this.value;
  }
}
