package org.psawesome.rsocketmongovue.utils.factory.form.model.type.marker;

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

public interface PsValue {
  <T> T getValue();

  <T> T setValue(T value);
}