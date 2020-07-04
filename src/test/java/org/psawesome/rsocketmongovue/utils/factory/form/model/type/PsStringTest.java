package org.psawesome.rsocketmongovue.utils.factory.form.model.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.psawesome.rsocketmongovue.utils.factory.form.model.PsNode;

import static org.psawesome.rsocketmongovue.utils.factory.form.model.PsNode.CDATA_STATE;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 4. Saturday
 */
class PsStringTest {
  @Test
  void initTest() {
    PsNode build = PsString.builder()
            .name("ps")
            .value("testValue")
            .state(CDATA_STATE.ACTIVE)
            .build();
    Assertions.assertNotNull(build);
    System.out.println(build.getClass().getName());
  }
}