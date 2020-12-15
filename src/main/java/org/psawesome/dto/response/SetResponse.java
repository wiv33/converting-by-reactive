package org.psawesome.dto.response;

import java.lang.reflect.Field;
import java.util.Arrays;
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

  default <T> Object getValue(String key, T targetInstance) {
    Class<?> clazz = targetInstance.getClass();
    if (Arrays.stream(clazz.getDeclaredFields())
            .noneMatch(s -> s.getName().equalsIgnoreCase(key))) {
      return null;
    }

    try {
      final Field declaredField = clazz.getDeclaredField(key);
      declaredField.setAccessible(true);
      return declaredField.get(targetInstance);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      System.out.printf("No Such Field [%s] in [%s]:%n", key, targetInstance.getClass().getName());
      return null;
    }
  }
}
