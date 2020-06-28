package org.psawesome.rsocketmongovue.domain.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
public class EntityToDto {

  @SuppressWarnings({"unchecked", "Duplicates"})
  public <T, R> Mono<R> transform(T entity, Class<R> result) {
    try {
      Constructor<?> declaredConstructor = Stream.of(Class.forName(result.getTypeName()).getDeclaredConstructors())
              .filter(s -> s.getParameterCount() == 0)
              .findFirst()
              .orElseThrow(NullPointerException::new)
              ;
      declaredConstructor.setAccessible(true);
      return Flux.fromStream(Stream.of(result.getDeclaredFields()))
              .reduce(((R) declaredConstructor.newInstance()), (res, field) -> {
                try {
                  Field entityField = entity.getClass().getDeclaredField(field.getName());
                  entityField.setAccessible(true);
                  Field dtoField = res.getClass().getDeclaredField(field.getName());
                  dtoField.setAccessible(true);
                  dtoField.set(res, entityField.get(entity));
                  System.out.println("dtoField = " + dtoField.get(res));
                  return res;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                  return res;
                }
              })
              .log();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}
