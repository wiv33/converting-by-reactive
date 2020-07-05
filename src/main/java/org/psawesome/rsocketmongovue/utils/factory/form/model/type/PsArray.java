package org.psawesome.rsocketmongovue.utils.factory.form.model.type;

import org.psawesome.rsocketmongovue.utils.factory.form.model.type.marker.PsValue;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role Array 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */

public final class PsArray implements PsValue<PsArray, List<?>> {
  private List<?> value = new LinkedList<>();

  @Override
  public PsArray getValue() {
    return this;
  }

  @Override
  public List<?> getImpl() {
    return value;
  }

  @Override
  public List<?> setImpl(List<?> value) {
    return this.value = value;
  }

}