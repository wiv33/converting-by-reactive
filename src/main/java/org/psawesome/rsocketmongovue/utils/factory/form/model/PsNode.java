package org.psawesome.rsocketmongovue.utils.factory.form.model;

import lombok.Data;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role 클라이언트의 값을 타입화 하여 포멧에게 전달한다.
 * @responsibility 입력값의 타입에 맞게 구현화
 * @cooperate {
 * input: 클라이언트가 필요한 포멧의 정보 값
 * output: PsForm 에게 자신(노드들)을 전달
 * }
 * @see org.psawesome.rsocketmongovue.utils.factory.form.model.type
 * @since 20. 7. 4. Saturday
 */

@Data
public class PsNode {

  private final PsType value;

  public PsNode(Map<String, Object> param) {
    this.value = PS_VALUE.classifier(param);
  }


  protected <R> R propNotNull(R target, String name) {
    return Objects.requireNonNull(target, String.format("must not null node [%s]", name));
  }

  private enum PS_VALUE {
    STRING("string", (Map<String, Object> map) -> new PsString()),
    DATE("date", (Map<String, Object> map) -> new PsDate()),
    MAP("map", (Map<String, Object> map) -> new PsMap()),
    ARRAY("array", (Map<String, Object> map) -> new PsArray());

    private final String type;
    private final Function<Map<String, Object>, ? extends PsType> psFunc;

    PS_VALUE(String type, Function<Map<String, Object>, ? extends PsType> psFunc) {
      this.type = type;
      this.psFunc = psFunc;
    }

    private static PsType classifier(Map<String, Object> param) {
      return Arrays.stream(PS_VALUE.values())
              .filter(v -> v.type.equals(param.get("type").toString().toLowerCase()))
              .peek(System.out::println)
              .findFirst()
              .orElseThrow(NullPointerException::new)
              .psFunc.apply(param);
    }

  }

  private enum NODE_STATE {
    ACTIVE("ACTIVE"),
    NONE("NONE"),
    TO_UPPER("TO_UPPER"),
    TO_LOWER("TO_LOWER");
    private final String state;
    NODE_STATE(String state) {
      this.state = state;
    }
  }
}
