package org.psawesome.rsocketmongovue.domain.user.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.user.entity.dto.PsUserDto;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 28. Sunday
 */
@SpringBootTest
class PsUserTest {

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  @Autowired
  ReactiveMongoOperations mongoOperations;


  @Test
  @DisplayName("setUp 시 save 확인")
  void testSetUpSaveMongo() {
    PsUser user = psUser();

    Publisher<PsUser> save = reactiveMongoTemplate.save(user);
    PsUserDto expected = PsUserDto.builder()
            .name("ps")
            .email("psk@gmail.com")
            .phone("010-0000-0000")
            .age(17)
            .build();

    StepVerifier.create(save)
            .consumeNextWith(response -> assertAll(
//                    () -> assertThrows(NullPointerException.class, () -> System.out.println("failTest = " + "failTest")),
                    () -> assertEquals(response, user),
                    () -> assertEquals(expected.getName(), response.getName()),
                    () -> assertEquals(expected.getPhone(), response.getPhone()),
                    () -> assertEquals(expected.getAge(), response.getAge()),
                    () -> assertEquals(expected.getEmail(), response.getEmail()))
            )
            .expectComplete()
            .verify()
    ;

  }

  private PsUser psUser() {
    return PsUser.builder()
            .name("ps")
            .phone("010-0000-0000")
            .email("psk@gmail.com")
            .age(17)
            .build();
  }

  @Test
  void testMongoTemplate() {

    mongoOperations.insert(PsUser.builder()
            .name("misa")
            .email("misa@gmail.com")
            .phone("010-2323-2323")
            .age(17)
            .build())
    ;

  }

  @Test
  @DisplayName("저장하고 조회한 객체 비교: findOne")
  void testFindOne() {
    /*
        debug
        2020-06-28 16:48:09.122 DEBUG 47887 --- [ntLoopGroup-2-2] o.s.d.m.core.ReactiveMongoTemplate       : findOne using query: { "name" : "ps"} fields: {} in db.collection: test.PS_USER
     */
    PsUser given = psUser();
    StepVerifier.create(reactiveMongoTemplate.save(given)
            .log()
            .then(reactiveMongoTemplate.findOne(new Query(where("uuid").is(given.getUuid())), PsUser.class).log()))
            .expectNext(new PsUser(given.getUuid(), "ps", "010-0000-0000", "psk@gmail.com", 17))
            .verifyComplete();


  }

}