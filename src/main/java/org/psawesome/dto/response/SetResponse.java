package org.psawesome.dto.response;

import org.psawesome.entity.PsChild;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public interface SetResponse {
  default <T, R> void accept(T request, R responseInstance) {
    final Class<?> responseClazz = responseInstance.getClass();
    Stream.of(responseClazz.getDeclaredFields())
            .forEach(field -> {
              try {
                final Field declaredField = responseClazz.getDeclaredField(field.getName());
                declaredField.setAccessible(true);

                final Field requestField = request.getClass().getDeclaredField(field.getName());
                requestField.setAccessible(true);

                final Object o = requestField.get(request);
                declaredField.set(responseInstance, o);
              } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e.getMessage());
              }
            });
  }
}
