package org.psawesome.rsocketmongovue.domain.user.entity.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import reactor.core.publisher.Flux;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
@DisplayName("transform dto in entity fields")
class PsUserDtoTest {

  PsUser entity;

  PsUserDto expect;

  PsUserDto actual;

  @BeforeEach
  void setUp() {
    entity = PsUser.builder()
            .name("ps")
            .phone("010")
            .email("psk")
            .age(17)
            .build();

    expect = PsUserDto.builder()
            .name("ps")
            .phone("010")
            .email("psk")
            .age(17)
            .build();
  }

  @Test
  @DisplayName("should be equal entity value and value in new PsUserDto().transform(entity) ")
  void testResult() {
    actual = new PsUserDto().transform(entity);
    assertEquals(expect, actual);
  }

  @Test
  @DisplayName("should be exist get field")
  void testTransform() throws Exception {
    // 1. Entity field 와 값을 조회한다.
    Field[] fields = entity.getClass().getFields();
    assertEquals(0, fields.length);

    Field[] declaredFields = entity.getClass().getDeclaredFields();
    assertEquals(4, declaredFields.length);

    Flux.fromStream(Stream.of(declaredFields))
            .reduce(PsUserDto.builder().build() ,(acc, field) -> {
              Object field1 = getField(field.getName());
              return acc;
            })
            .log()
            .doOnError(Throwable::new)
            .subscribe();

  }


  private Object getField(String name) {
    try {
      Field declaredField = entity.getClass().getDeclaredField(name);
      declaredField.setAccessible(true);
      return declaredField.get(entity);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      return null;
    }

  }
}