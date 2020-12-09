package org.psawesome.dto.response;

import org.junit.jupiter.api.Test;
import org.psawesome.converter.ElementsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;


@SpringBootTest
class ElementsByDbTest {

  @Autowired
  ElementsRepo repo;

  @Test
  void testTransform() {
    final Flux<ElementsByDb> elementsByDbFlux = repo.selectAllByTemplateSeq(2);

    final ElementsWrapper<ElementsByDb> elementsByDb = new ElementsWrapper<>(elementsByDbFlux.toStream().collect(Collectors.toList()));

    elementsByDb.getElements()
            .forEach(System.out::println);
  }
}