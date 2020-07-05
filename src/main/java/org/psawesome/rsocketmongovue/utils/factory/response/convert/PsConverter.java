package org.psawesome.rsocketmongovue.utils.factory.response.convert;

import java.util.function.Function;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @responsibility
 * @role
 * @cooperate
 * @since 20. 7. 4. Saturday
 */
@FunctionalInterface
public interface PsConverter<T, R> extends Function<T, R> {

}
