package org.psawesome.rsocketmongovue.utils.factory.form.model.type;

import org.psawesome.rsocketmongovue.utils.factory.form.model.type.marker.PsValue;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role String (단일) 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */
public final class PsString implements PsValue<PsString, String> {

  private String value = "";

  @Override
  public PsString getValue() {
    return this;
  }

  @Override
  public String getImpl() {
    return this.value;
  }

  @Override
  public String setImpl(String value) {
    return this.value = value;
  }
}
