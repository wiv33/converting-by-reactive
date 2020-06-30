package org.psawesome.rsocketmongovue.domain.user.entity.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.math.BigDecimal;
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
    // tag::oldVersion[]
    actual = new PsUserDtoTest().transform(entity).block();
    assertEquals(expected, actual);
    // end::oldVersion[]
  }

  // tag::AccessTestClass[]

  // tag::declared Classes[]
  @SuppressWarnings("all")
  class TestAccessField {
    private String failString;
    int failInt;
    protected BigDecimal failBigDecimal;
    public Double accessDouble;
  }

  @SuppressWarnings("unused")
  static class TestStaticAccessField {
    private String failString;
    int failInt;
    protected BigDecimal failBigDecimal;
    public Double accessDouble;
  }

  // end::declared Classes[]
  @Test
  @DisplayName("class constructor 5")
  void testAccess() {
    Field[] fields = TestAccessField.class.getFields();
    assertEquals(1, fields.length);

    Field[] declaredFields = TestAccessField.class.getDeclaredFields();
    assertEquals(5, declaredFields.length);
/*
    Stream.of(declaredFields)
            .map(Field::getName)
            .forEach(System.out::println);
*/

  }

  @Test
  @DisplayName("static class constructor 4")
  void testStaticAccess() {
    Field[] fields = TestStaticAccessField.class.getFields();
    assertEquals(1, fields.length);

    Field[] declaredFields = TestStaticAccessField.class.getDeclaredFields();
    assertEquals(4, declaredFields.length);
  }

  // end::AccessTestClass[]

  @Test
  @DisplayName("should be exist get field")
  void testTransform() {
    // 1. Entity field 와 값을 조회한다.
    // tag::private []
    Field[] fields = entity.getClass().getFields();
    assertEquals(0, fields.length);
    // end::private[]

    // tag::선언된 fields 조회[]
    Field[] declaredFields = entity.getClass().getDeclaredFields();
    assertEquals(5, declaredFields.length);
    // end::선언된 fields 조회[]

    Flux.fromStream(Stream.of(declaredFields))
            .reduce(PsUserDto.builder().build(), (psUserDto, field) -> {
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
            .doOnError(Throwable::printStackTrace)
            .subscribe(actualDto -> assertAll(
                    () -> assertEquals(expected.getUuid(), actualDto.getUuid()),
                    () -> assertEquals(expected.getAge(), actualDto.getAge()),
                    () -> assertEquals(expected.getName(), actualDto.getName()),
                    () -> assertEquals(expected.getPhone(), actualDto.getPhone()),
                    () -> assertEquals(expected.getEmail(), actualDto.getEmail())
            ));
  }


  public Mono<PsUserDto> transform(PsUser entity) {
    return Flux.fromStream(Stream.of(PsUserDto.class.getDeclaredFields()))
            .reduce(PsUserDto.builder().build(), (psUserDto, field) -> {
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
            ;
  }
}