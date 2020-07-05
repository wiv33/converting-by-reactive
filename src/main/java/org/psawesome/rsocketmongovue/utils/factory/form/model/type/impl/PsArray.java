package org.psawesome.rsocketmongovue.utils.factory.form.model.type.impl;

import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsValue;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role Array 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */

public final class PsArray<U> implements PsValue<List<? extends U>> {
  private List<? extends U> value = new LinkedList<>();

  @Override
  public PsArray<U> getValue() {
    return this;
  }

  @Override
  public List<? extends U> getImpl() {
    return value;
  }

  @Override
  public List<? extends U> setImpl(List<? extends U> value) {
    return this.value = value;
  }

}