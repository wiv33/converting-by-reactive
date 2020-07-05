package org.psawesome.rsocketmongovue.utils.factory.form.model.type;

import org.psawesome.rsocketmongovue.utils.factory.form.model.type.marker.PsValue;

import java.util.LinkedHashMap;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role Map 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */

public final class PsMap implements PsValue {
  private LinkedHashMap<String, Object> value;

  public PsMap() {
    this.value = new LinkedHashMap<>();
  }

  @Override
  public  LinkedHashMap<String, Object> getValue() {
    return value;
  }

  @Override
  public <T> T setValue(T value) {
    this.value.putAll((LinkedHashMap<String, Object>) value);
    return ((T) this.value);
  }

}