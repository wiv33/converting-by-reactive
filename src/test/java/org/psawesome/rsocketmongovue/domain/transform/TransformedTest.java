package org.psawesome.rsocketmongovue.domain.transform;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
 * @since 20. 7. 11. Saturday
 */
public class TransformedTest {

  @Test
  void testArgsForTransformed() {

  }


  public static List<Map<String, Object>> transformForTransformed() {
    var strLinux = "";
    ArrayList<Map<String, Object>> params;
    try {
      strLinux = Files.readString(Path.of("input-one-depth.json"));
      params = new ObjectMapper().readValue(strLinux, new TypeReference<>() {
      });
    } catch (IOException e) {
      throw new RuntimeException("error ! " + e.getMessage());
    }
    return params;
  }

}