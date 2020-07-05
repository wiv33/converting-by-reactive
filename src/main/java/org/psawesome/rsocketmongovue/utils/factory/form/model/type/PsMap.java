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

public final class PsMap implements PsValue<PsMap, LinkedHashMap<String, Object>> {
  private LinkedHashMap<String, Object> value= new LinkedHashMap<>();;

  @Override
  public PsMap getValue() {
    return this;
  }

  @Override
  public LinkedHashMap<String, Object> getImpl() {
    return this.value;
  }

  @Override
  public LinkedHashMap<String, Object> setImpl(LinkedHashMap<String, Object> value) {
    return this.value = value;
  }

}