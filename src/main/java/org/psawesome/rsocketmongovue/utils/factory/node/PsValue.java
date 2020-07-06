package org.psawesome.rsocketmongovue.utils.factory.node;

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

interface PsValue<I> {
  @JsonIgnore
  PsValue<I> getValue();

  I getImpl();

  I setImpl(I value);
}
