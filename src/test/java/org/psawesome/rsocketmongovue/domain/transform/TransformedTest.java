package org.psawesome.rsocketmongovue.domain.transform;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.transform.request.TransformedRequest;
import org.psawesome.rsocketmongovue.domain.transform.request.TransformedRequest.TRANSFORMED_TYPE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 11. Saturday
 */
class TransformedTest {

  TransformedRequest request;

  Transformed transformed;

  @BeforeEach
  void setUp() {
    request = new TransformedRequest(transformForTransformed(), TRANSFORMED_TYPE.XML);
  }


  @Test
  void testArgsForTransformed() {
    transformed = new Transformed(request);
    assertNotNull(transformed);
    assertNotNull(transformed.getRequest());
    assertNotNull(transformed.getRequest().getFrom());
    assertNotNull(transformed.getRequest().getTo());

    assertEquals(transformed.getRequest().getTo().name(), "XML");
  }


  private List<Map<String, Object>> transformForTransformed() {
    var strLinux = "";
    ArrayList<Map<String, Object>> params;
    try {
      strLinux = Files.readString(Path.of("/home/ps/dev/java/IdeaProjects/rsocket-mongo-vue/src/test/java/org/psawesome/rsocketmongovue/domain/node/input-one-depth.json"));
      params = new ObjectMapper().readValue(strLinux, new TypeReference<>() {
      });
    } catch (IOException e) {
      throw new RuntimeException("error ! " + e.getMessage());
    }
    return params;
  }

}