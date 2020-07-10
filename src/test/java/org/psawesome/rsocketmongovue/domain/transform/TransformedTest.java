package org.psawesome.rsocketmongovue.domain.transform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.domain.transform.request.TransformedRequest;
import org.psawesome.rsocketmongovue.domain.transform.request.TransformedRequest.TRANSFORMED_TYPE;

import java.util.LinkedHashMap;

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
    request = new TransformedRequest(new LinkedHashMap<>(), TRANSFORMED_TYPE.XML);
  }

  @Test
  void testArgsForTransformed() {
    transformed = new Transformed(request);
    assertNotNull(transformed);
    assertNotNull(transformed.request);
  }
}