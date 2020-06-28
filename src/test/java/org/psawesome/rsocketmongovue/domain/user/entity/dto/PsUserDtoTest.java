package org.psawesome.rsocketmongovue.domain.user.entity.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import reactor.core.publisher.Flux;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
@DisplayName("transform dto in entity fields")
class PsUserDtoTest {

  PsUser entity;

  PsUserDto expected;

  PsUserDto actual;

  @BeforeEach
  void setUp() {
    entity = PsUser.builder()
            .name("ps")
            .phone("010")
            .email("psk")
            .age(17)
            .build();

    expected = PsUserDto.builder()
            .uuid(entity.getUuid())
            .name("ps")
            .phone("010")
            .email("psk")
            .age(17)
            .build();
  }

  @Test
  @DisplayName("should be equal entity value and value in new PsUserDto().transform(entity) ")
  void testResult() {
    actual = new PsUserDto().transform(entity).block();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("should be exist get field")
  void testTransform() throws Exception {
    // 1. Entity field 와 값을 조회한다.
    Field[] fields = entity.getClass().getFields();
    assertEquals(0, fields.length);

    Field[] declaredFields = entity.getClass().getDeclaredFields();
    assertEquals(5, declaredFields.length);

    Flux.fromStream(Stream.of(declaredFields))
            .reduce(PsUserDto.builder().build() ,(psUserDto, field) -> {
              try {
                Field entityField = entity.getClass().getDeclaredField(field.getName());
                entityField.setAccessible(true);
                Field dtoField = psUserDto.getClass().getDeclaredField(field.getName());
                dtoField.setAccessible(true);
                dtoField.set(psUserDto, entityField.get(entity));
                return psUserDto;
              } catch (NoSuchFieldException | IllegalAccessException e) {
                return psUserDto;
              }
            })
            .log()
            .doOnError(Throwable::new)
            .subscribe(actualDto -> assertAll(
                    () -> assertEquals(expected.getUuid(), actualDto.getUuid()),
                    () -> assertEquals(expected.getAge(), actualDto.getAge()),
                    () -> assertEquals(expected.getName(), actualDto.getName()),
                    () -> assertEquals(expected.getPhone(), actualDto.getPhone()),
                    () -> assertEquals(expected.getEmail(), actualDto.getEmail())
            ));

  }


}