package org.psawesome.rsocketmongovue.domain.user.entity.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.common.EntityToDto;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.res.PsUserResponse;
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

  // tag::abstractionTest[]
  Mono<PsUserResponse> abstractionExcepted;
  Mono<PsUserResponse> abstractionActual;
  private Mono<PsUserDto> monoExpected;

  // end::abstractionTest[]
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

    monoExpected = Mono.just(PsUserDto.builder()
            .uuid(entity.getUuid())
            .name("ps")
            .phone("010")
            .email("psk")
            .age(17)
            .build());

    abstractionExcepted = Mono.just(PsUserResponse.builder()
            .uuid(entity.getUuid())
            .email(entity.getEmail())
            .build());
  }

  @Test
  @DisplayName("should be equal entity value and value in new PsUserDto().transform(entity) ")
  void testResult() {
    // tag::oldVersion[]
//    actual = new PsUserDto().transform(entity).block();
//    assertEquals(expected, actual);
    // end::oldVersion[]
    Mono<PsUserDto> transformActual = EntityToDto.<PsUser, PsUserDto>transform(entity);
    assertEquals(monoExpected, transformActual);
  }

  // tag::AccessTestClass[]

  class TestAccessField {
    private String failString;
    int failInt;
    protected BigDecimal failBigDecimal;
    public Double accessDouble;
  }

  @Test
  void testAccess() {
    Field[] fields = TestAccessField.class.getFields();
    assertEquals(1, fields.length);

    Field[] declaredFields = TestAccessField.class.getDeclaredFields();
    Stream.of(declaredFields)
            .map(Field::getName)
            .forEach(System.out::println);

    assertEquals(5, declaredFields.length);
  }
  // end::AccessTestClass[]

  @Test
  @DisplayName("should be exist get field")
  void testTransform() {
    // 1. Entity field 와 값을 조회한다.
    // tag::private []
    Field[] fields = entity.getClass().getFields();
    assertEquals(0, fields.length);
    // end::tagname[]

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
            .doOnError(Throwable::new)
            .subscribe(actualDto -> assertAll(
                    () -> assertEquals(expected.getUuid(), actualDto.getUuid()),
                    () -> assertEquals(expected.getAge(), actualDto.getAge()),
                    () -> assertEquals(expected.getName(), actualDto.getName()),
                    () -> assertEquals(expected.getPhone(), actualDto.getPhone()),
                    () -> assertEquals(expected.getEmail(), actualDto.getEmail())
            ));
  }


  @Test
  @DisplayName("다른 타입도 transform 가능하도록 추가")
  void testAbstractionTransform() {
    abstractionActual = EntityToDto.<PsUser, PsUserResponse>transform(entity);
    assertEquals(abstractionExcepted, abstractionActual);
  }
}