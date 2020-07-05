package org.psawesome.rsocketmongovue.utils.factory.form.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsArray;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsDate;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsMap;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsString;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @since 20. 7. 4. Saturday
 */
class PsNodeTest {
  // input
  String str;
  ObjectMapper mapper;
  List<LinkedHashMap<String, Object>> linked;
  Flux<LinkedHashMap<String, Object>> publisher;

  @BeforeEach
  void setUp() throws IOException {
    str = Files.readString(Path.of("/home/ps/dev/java/IdeaProjects/rsocket-mongo-vue/src/test/java/org/psawesome/rsocketmongovue/utils/factory/form/model/input-one-depth.json"));
    mapper = new ObjectMapper();
    linked = mapper.readValue(str, new TypeReference<>() {
    });

    publisher = Flux.fromStream(getStream())
            .log();
  }

  private Stream<LinkedHashMap<String, Object>> getStream() {
    return linked.stream().peek(System.out::println);
  }

  @Test
  @Deprecated
  void testMultiDepthJson() throws IOException {
    String str = Files.readString(Path.of("/home/ps/dev/java/IdeaProjects/rsocket-mongo-vue/src/test/java/org/psawesome/rsocketmongovue/utils/factory/form/model/input-multi-depth.json"));
    ObjectMapper mapper = new ObjectMapper();
    LinkedHashMap<String, Object> stringObjectMap = mapper.readValue(str, new TypeReference<>() {
    });
  }

  @Test
  void testOneDepthJson() throws IOException {

    StepVerifier.create(Flux.fromStream(getStream())
            .map(PsNode::new)
            .map(node -> node.getValue().getClass().getName())
            .log()
    )
            .expectNext(PsMap.class.getName(),
                    PsString.class.getName(),
                    PsString.class.getName(),
                    PsDate.class.getName(),
                    PsArray.class.getName(),
                    PsMap.class.getName(),
                    PsDate.class.getName(),
                    PsString.class.getName(),
                    PsString.class.getName())
            .verifyComplete();
  }

  @Test
  void testNodeGetValue() {
    StepVerifier.create(Flux.fromStream(getStream())
            .map(PsNode::new)
            .map(objectPsNode -> objectPsNode.getValue().getValue())
            .log())
            .expectNextCount(9)
            .verifyComplete();
  }

  @Test
  void testNodeSet() {
    StepVerifier.create(publisher.map(PsNode::new))
            .consumeNextWith(node -> assertAll(
                    () -> assertNull(node.getParent()),
                    () -> assertNotNull(node.getName()),
                    () -> assertNotNull(node.getAttributes()),
                    () -> assertNotNull(node.getCdata()),
                    () -> assertNotNull(node.getSpell())
            ))
            .consumeNextWith(node -> assertAll(
                    () -> assertNotNull(node.getName()),
                    () -> assertNotNull(node.getAttributes()),
                    () -> assertNotNull(node.getParent()),
                    () -> assertNotNull(node.getCdata()),
                    () -> assertNotNull(node.getSpell())
            ))
            .thenCancel()
            .verify();
  }

  @Test
  void testGenericImplement() {
    LinkedHashMap<String, Object> param = this.linked.get(4);
    PsNode<PsArray<Map<String, Object>>, List<Map<String, Object>>> node = new PsNode<>(param);
    Map<String, Object> expected = new LinkedHashMap<>();
    List<Map<String, Object>> actual = node.getValue().setImpl(testParam(expected));

    assertEquals(expected.get("myFirst"), actual.get(0).get("myFirst"));
  }

  private ArrayList<Map<String, Object>> testParam(Map<String, Object> stringObjectLinkedHashMap) {
    stringObjectLinkedHashMap.put("myFirst", "test");
    ArrayList<Map<String, Object>> linkedHashMaps = new ArrayList<>();
    linkedHashMaps.add(stringObjectLinkedHashMap);
    return linkedHashMaps;
  }
}