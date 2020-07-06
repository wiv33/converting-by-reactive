package org.psawesome.rsocketmongovue.utils.factory.node.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 4. Saturday
 */

public interface PsValue<I> {
  @JsonIgnore
  PsValue<I> getValue();

  I getImpl();

  I setImpl(I value);
}
