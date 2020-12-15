package org.psawesome.dto.response;

import org.junit.jupiter.api.Test;
import org.psawesome.converter.ElementsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;


@SpringBootTest
class ElementsByDbTest {

  @Autowired
  ElementsRepo repo;

  @Test
  void testTransform() {
    final Mono<ElementsWrapper<ElementsByDb>> elementsByDbFlux = repo.selectAllByTemplateSeq(2);

    final ElementsWrapper<ElementsByDb> elementsByDb = new ElementsWrapper<>(elementsByDbFlux.block().getElements());

    elementsByDb.getElements()
            .forEach(System.out::println);
  }
}