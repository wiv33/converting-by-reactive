package org.psawesome.rsocketmongovue.utils.factory.form.model.type;

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
  PsValue<I> getValue();

  I getImpl();

  I setImpl(I value);
}
