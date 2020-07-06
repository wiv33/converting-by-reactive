package org.psawesome.rsocketmongovue.utils.factory.form.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.PsValue;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.impl.PsArray;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.impl.PsDate;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.impl.PsMap;
import org.psawesome.rsocketmongovue.utils.factory.form.model.type.impl.PsString;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role 클라이언트의 값을 타입화 하여 Tree에게 전달한다.
 * @responsibility 입력값의 타입에 맞게 구현화
 * @cooperate {
 * input: PsTree 로부터 클라이언트가 필요한 포멧의 정보 값
 * output: PsTree 에게 자신(노드)의 완성된 형식을 전달
 * }
 * @see org.psawesome.rsocketmongovue.utils.factory.form.model.type
 * @since 20. 7. 4. Saturday
 */

@Getter
public class PsNode<I> {
  private String name;
  @JsonIgnore
  private Map<String, Object> attributes;
  private String parent;
  @JsonIgnore
  private NODE_STATE cdata;
  @JsonIgnore
  private NODE_STATE spell;

  private PsValue<I> value;

  @JsonProperty("value")
  public I getValueImpl() {
    return value.getImpl();
  }

  public PsNode(Map<String, Object> param) {
    this.name = propNotNull((String) param.get("name"), "name");
    this.cdata = propNotNull(NODE_STATE.getState(param.get("cdata")), "cdata");
    this.spell = propNotNull(NODE_STATE.getState(param.get("spell")), "spell");
    this.attributes = (Map<String, Object>) param.get("attributes");
    this.parent = (String) param.getOrDefault("parent", "");
    this.value = PS_VALUE_FORMAT.classifier(param);
  }

  protected <R> R propNotNull(R target, String name) {
    return Objects.requireNonNull(target, String.format("must not null node [%s]", name));
  }

  private enum PS_VALUE_FORMAT {
    STRING("string", () -> new PsString()),
    DATE("date", () -> new PsDate()),
    MAP("map", () -> new PsMap()),
    ARRAY("array", () -> new PsArray<>());

    private final String type;
    private final Supplier<? extends PsValue<?>> transform;

    PS_VALUE_FORMAT(String type, Supplier<? extends PsValue<?>> transform) {
      this.type = type;
      this.transform = transform;
    }

    @SuppressWarnings("unchecked")
    private static <I> PsValue<I> classifier(Map<String, Object> param) {
      return (PsValue<I>)Arrays.stream(PS_VALUE_FORMAT.values())
              .filter(v -> v.type.equalsIgnoreCase(param.get("type").toString().toLowerCase()))
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
//              .peek(System.out::println)
              .filter(v -> v.state.equalsIgnoreCase(state.toString().toLowerCase()))
              .findFirst()
              .orElseThrow(NullPointerException::new)
              ;
    }
  }
}
