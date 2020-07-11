package org.psawesome.rsocketmongovue.domain.node.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 5. Sunday
 */
class PsArrayTest extends PsNodeTest {

  @Test
  void testGenericImplement() {
    LinkedHashMap<String, Object> param = super.linked.get(4);
    PsNode<List<Map<String, Object>>> node = new PsNode<>(param);
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