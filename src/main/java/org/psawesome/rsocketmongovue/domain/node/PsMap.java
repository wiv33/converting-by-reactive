package org.psawesome.rsocketmongovue.domain.node;

import java.util.LinkedHashMap;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role Map 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */

 final class PsMap<U> implements PsValue<LinkedHashMap<String, ? extends U>> {
  private LinkedHashMap<String, ? extends U> impl = new LinkedHashMap<>();
  ;

  @Override
  public PsMap<U> getValue() {
    return this;
  }

  @Override
  public LinkedHashMap<String, ? extends U> getImpl() {
    return this.impl;
  }

  @Override
  public LinkedHashMap<String, ? extends U> setImpl(LinkedHashMap<String, ? extends U> impl) {
    return this.impl = impl;
  }

}