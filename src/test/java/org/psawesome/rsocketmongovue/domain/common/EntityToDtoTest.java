package org.psawesome.rsocketmongovue.domain.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.PsUserDto;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.res.PsUserResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
class EntityToDtoTest {

  PsUser entity;

  // tag::abstractionTest[]
  PsUserResponse abstractionExcepted;
  PsUserResponse abstractionActual;
  PsUserDto monoExpected;

  // end::abstractionTest[]

  @BeforeEach
  void setUp() {
    setSingleTransferData();


  }

  @Test
  void testNewInstance() {
    PsUserDto transformActual = new EntityToDto().transfer(entity, PsUserDto.class).block();
    assertEquals(monoExpected, transformActual);
  }


  @Test
  @DisplayName("다른 타입도 transform 가능하도록 추가")
  void testAbstractionTransfer() {
    abstractionActual = new EntityToDto().transfer(entity, PsUserResponse.class).block();
    assertEquals(abstractionExcepted, abstractionActual);
  }


  @Test
  void testListTransfer() {
    // TODO n 개의 Publisher<T> entity to Publisher<R> response

  }


  // tag::테스트를 위한 데이터 생성[]
  private void setSingleTransferData() {
    entity = PsUser.builder()
            .name("ps")
            .phone("010")
            .email("psk")
            .age(17)
            .build();

    monoExpected = PsUserDto.builder()
            .uuid(entity.getUuid())
            .name("ps")
            .phone("010")
            .email("psk")
            .age(17)
            .build();

    abstractionExcepted = PsUserResponse.builder()
            .uuid(entity.getUuid())
            .email(entity.getEmail())
            .build();
  }
  // end::테스트를 위한 데이터 생성[]


}