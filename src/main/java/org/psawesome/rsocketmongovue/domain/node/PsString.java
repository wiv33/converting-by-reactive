package org.psawesome.rsocketmongovue.domain.node;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role String (단일) 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */
final class PsString implements PsValue<String> {

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
