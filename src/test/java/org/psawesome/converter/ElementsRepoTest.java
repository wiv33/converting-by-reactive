package org.psawesome.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@SpringBootTest
class ElementsRepoTest {
  @Autowired
  DatabaseClient databaseClient;

  @Autowired
  ElementsRepo repo;

  @BeforeEach
  void setUp() {

  }

  @Test
  void shouldAvailableDBConnection() {
    final ElementsRepo mock = Mockito.mock(ElementsRepo.class);

    when(mock.selectAllByTemplateSeq(anyInt())).thenReturn(Mono.just(any()));

    mock.selectAllByTemplateSeq(2)
            .as(StepVerifier::create)
            .expectComplete()
            .verify();

    verify(mock).selectAllByTemplateSeq(2);

    verifyNoMoreInteractions(mock);
  }

  @Test
  void shouldContainsAttributeInChild() {
    repo.selectAllByTemplateSeq(3)
            .as(StepVerifier::create)
            .recordWith(ArrayList::new)
            .consumeRecordedWith(consumer -> {
              consumer.forEach(s -> assertAll(
                      () -> assertEquals(39, s.getElements().get(0).getChild().getChildSeq())
              ));
            })
            .verifyComplete();

  }
}