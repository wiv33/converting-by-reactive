package org.psawesome.rsocketmongovue.domain.node.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.node.model.NodeMaker;
import org.psawesome.rsocketmongovue.domain.node.model.PsNode;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 7. Tuesday
 */
class NodeMakerTest implements NodeMaker {


  NodeMaker maker;

  PsNode<String> string;
  PsNode<List<?>> list;
  PsNode<LocalDateTime> date;
  PsNode<Map<String, Object>> map;

  List<LinkedHashMap<String, Object>> jsonMap;

  Flux<LinkedHashMap<String, Object>> publisher;
  Path path;


  @BeforeEach
  void setUp() throws IOException {
    String os = System.getenv().get("os");
    System.out.println("os = " + os);
    path = Paths.get("/home/ps/dev/java/IdeaProjects/rsocket-mongo-vue/src/test/java/org/psawesome/rsocketmongovue/utils/factory/node/input-one-depth.json");
//    maker.init(jsonMap.stream());
  }

  @Test
  void testFilePath() {
    StepVerifier.create(this.init(path).map(PsNode::getValueImpl))
            .expectNextCount(9)
            .verifyComplete();
  }


}