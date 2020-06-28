package org.psawesome.rsocketmongovue.domain.user.entity.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;

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
            .build();
    expect = PsUserDto.builder()
            .name("ps")
            .phone("010")
            .email("psk")
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
  void testTransform() {
    // 1. Entity field 와 값을 조회한다.

  }


}