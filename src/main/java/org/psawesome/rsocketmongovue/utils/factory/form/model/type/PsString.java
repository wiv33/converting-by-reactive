package org.psawesome.rsocketmongovue.utils.factory.form.model.type;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.psawesome.rsocketmongovue.utils.factory.form.model.PsNode;

import java.util.Map;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role String (단일) 값을 저장
 * @responsibility
 * @cooperate
 * @since 20. 7. 4. Saturday
 */
@EqualsAndHashCode(callSuper = true)
public final class PsString extends PsNode {
  private final String value;

  @Builder
  protected PsString(String name, Map<String, String> attribute, CDATA_STATE state, String value) {
    super(name, attribute, state);
    this.value = value;
  }
}
