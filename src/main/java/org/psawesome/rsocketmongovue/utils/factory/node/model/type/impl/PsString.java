package org.psawesome.rsocketmongovue.utils.factory.node.model.type.impl;

import org.psawesome.rsocketmongovue.utils.factory.node.model.type.PsValue;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role String (단일) 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */
public final class PsString implements PsValue<String> {

  private String impl = "";

  @Override
  public PsString getValue() {
    return this;
  }

  @Override
  public String getImpl() {
    return this.impl;
  }

  @Override
  public String setImpl(String impl) {
    return this.impl = impl;
  }
}
