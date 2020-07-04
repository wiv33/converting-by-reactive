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
import java.util.LinkedHashMap;
import java.util.List;

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

  @BeforeEach
  void setUp() {

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
    String str = Files.readString(Path.of("/home/ps/dev/java/IdeaProjects/rsocket-mongo-vue/src/test/java/org/psawesome/rsocketmongovue/utils/factory/form/model/input-one-depth.json"));
    ObjectMapper mapper = new ObjectMapper();
    List<LinkedHashMap<String, Object>> linked = mapper.readValue(str, new TypeReference<>() {
    });

    StepVerifier.create(Flux.fromStream(linked.stream())
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
}