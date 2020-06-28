package org.psawesome.rsocketmongovue.domain.user.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.PsUserDto;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
@SpringBootTest
class PsUserTest {

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  @Autowired
  ReactiveMongoOperations operations;

  @Test
  @DisplayName("setUp 시 save 확인")
  void testSetUpSaveMongo() {
    PsUser user = PsUser.builder()
            .name("ps")
            .phone("010-0000-0000")
            .email("psk@gmail.com")
            .build();

    Publisher<PsUser> save = reactiveMongoTemplate.save(user);
    PsUserDto expected = PsUserDto.builder()
            .name("ps")
            .email("psk@gmail.com")
            .phone("010-0000-0000")
            .build();

    StepVerifier.create(save).consumeNextWith(response ->
            assertAll(
//                    () -> assertThrows(NullPointerException.class, () -> System.out.println("failTest = " + "failTest")),
                    () -> assertEquals(response, user),
                    () -> assertEquals(expected.getName(), response.getName()),
                    () -> assertEquals(expected.getPhone(), response.getPhone()),
                    () -> assertEquals(expected.getEmail(), response.getEmail())
            )
    )
            .expectComplete()
            .verify()
    ;

  }
}