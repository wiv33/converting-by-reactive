package org.psawesome.rsocketmongovue.utils.factory.form.model;

import lombok.Getter;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsArray;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsDate;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsMap;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsString;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.marker.PsValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

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

@Getter
public class PsNode<T> {
  private String name;
  private Map<String, Object> attributes;
  private String parent;
  private NODE_STATE cdata;
  private NODE_STATE spell;
  private PsValue value;

  public PsNode(Map<String, Object> param) {
    this.name = propNotNull((String) param.get("name"), "name");
    this.attributes = (Map<String, Object>) param.get("attributes");
    this.parent = (String) param.get("parent");
    this.cdata = propNotNull(NODE_STATE.getState(param.get("cdata")), "cdata");
    this.spell = propNotNull(NODE_STATE.getState(param.get("spell")), "spell");
    this.value = PS_VALUE_FORMAT.classifier(param);
  }

  protected <R> R propNotNull(R target, String name) {
    return Objects.requireNonNull(target, String.format("must not null node [%s]", name));
  }

  private enum PS_VALUE_FORMAT {
    STRING("string", () -> new PsString()),
    DATE("date", () -> new PsDate()),
    MAP("map", () -> new PsMap()),
    ARRAY("array", () -> new PsArray());

    private final String type;
    private final Supplier<? extends PsValue> transform;

    PS_VALUE_FORMAT(String type, Supplier<? extends PsValue> transform) {
      this.type = type;
      this.transform = transform;
    }

    private static PsValue classifier(Map<String, Object> param) {
      return Arrays.stream(PS_VALUE_FORMAT.values())
              .filter(v -> v.type.equals(param.get("type").toString().toLowerCase()))
//              .peek(System.out::println)
              .findFirst()
              .orElseThrow(NullPointerException::new)
              .transform.get();
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

    public static NODE_STATE getState(Object state) {
      return Arrays.stream(NODE_STATE.values())
              .peek(System.out::println)
              .filter(v -> v.state.equalsIgnoreCase(state.toString().toLowerCase()))
              .findFirst()
              .orElseThrow(NullPointerException::new)
              ;
    }
  }
}
