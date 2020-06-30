package org.psawesome.rsocketmongovue.domain.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.PsUser;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.PsUserDto;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.res.PsUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
@SpringBootTest
class EntityToDtoTest {

  PsUser entity;

  // tag::abstractionTest[]
  PsUserResponse abstractionExcepted;
  PsUserDto monoExpected;
  // end::abstractionTest[]

  @Autowired
  EntityToDto toDto;

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  @BeforeEach
  void setUp() {
//    toDto = new EntityToDto();
    setSingleTransferData();
  }

  @Test
  void testNewInstance() {
    PsUserDto transformActual = toDto.transfer(entity, PsUserDto.class).block();
    assertEquals(monoExpected, transformActual);
  }


  @Test
  @DisplayName("다른 타입도 transform 가능하도록 추가")
  void testAbstractionTransfer() {
    PsUserResponse abstractionActual = toDto.transfer(entity, PsUserResponse.class).block();
    assertEquals(abstractionExcepted, abstractionActual);
  }

  // tag::list transfer 작업 중단[]

/*
  @Test
  @DisplayName("N 개의 Entity Transfer :: 최종 result")
  void testListTransfer() {
    // TODO n 개의 Publisher<T> entity to Publisher<R> response
    Flux<PsUser> given = this.reactiveMongoTemplate.findAll(PsUser.class);
    Flux<PsUserResponse> actual = toDto.transfer(given, PsUserResponse.class);
    given.zipWith(actual).subscribe(tuple ->
                    // @formatter:off
            assertAll(
              () -> assertEquals(tuple.getT1().getUuid(), tuple.getT2().getUuid()),
              () -> assertEquals(tuple.getT1().getEmail(), tuple.getT2().getEmail())
            )
                    // @formatter:on
    );
  }
*/
// end::list transfer 작업 중단[]

  // tag::findAll[]
/*
  public <T, R> Flux<R> transfer(Publisher<T> entities, Class<R> result) {
      return Flux.from(entities);
  }
 */

  // end::findAll[]


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