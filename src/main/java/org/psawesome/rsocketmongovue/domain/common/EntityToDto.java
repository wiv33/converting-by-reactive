package org.psawesome.rsocketmongovue.domain.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
public class EntityToDto {

  // tag::public methods[]

  public <T, R> Mono<R> transfer(T entity, Class<R> result) {
    return Flux.fromStream(getDtoFields(result))
            .reduce(getInitial(result),
                    transferToDto(entity))
            .log();
  }

  @SuppressWarnings({"unchecked"})
  private <R> R getInitial(Class<R> result) {
    return (R) initial(getNoArgConstructor(result));
  }

  // end::public methods[]

  private <R> Constructor<?> getNoArgConstructor(Class<R> result) {
    Class<?> aClass = null;
    try {
      aClass = Class.forName(result.getTypeName());
    } catch (ClassNotFoundException e) {
      throw new ClassCastException(e.getMessage());
    }
    return Stream.of(aClass.getDeclaredConstructors())
            .filter(s -> s.getParameterCount() == 0)
            .findFirst()
            .orElseThrow(NullPointerException::new);
  }

  private Object initial(Constructor<?> declaredConstructor) {
    declaredConstructor.setAccessible(true);
    try {
      return declaredConstructor.newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private <R> Stream<Field> getDtoFields(Class<R> result) {
    return Stream.of(result.getDeclaredFields());
  }

  private <T, R> BiFunction<R, Field, R> transferToDto(T entity) {
    return (res, field) -> dto(entity, res, field);
  }

  private <T, R> R dto(T entity, R res, Field field) {
    try {
      Field entityField = getField(entity, field);
      Field dtoField = getField(res, field);
      dtoField.set(res, entityField.get(entity));
    } catch (IllegalAccessException | NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
    return res;
  }

  private <T> Field getField(T target, Field field) throws NoSuchFieldException {
    Field resultField = target.getClass().getDeclaredField(field.getName());
    resultField.setAccessible(true);
    return resultField;
  }
}
