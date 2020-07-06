package org.psawesome.rsocketmongovue.utils.factory.node;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role Array 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */

final class PsArray<U> implements PsValue<List<? extends U>> {
  private List<? extends U> impl = new LinkedList<>();

  @Override
  public PsArray<U> getValue() {
    return this;
  }

  @Override
  public List<? extends U> getImpl() {
    return impl;
  }

  @Override
  public List<? extends U> setImpl(List<? extends U> impl) {
    return this.impl = impl;
  }

}