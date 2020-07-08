package org.psawesome.rsocketmongovue.utils.factory.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role 클라이언트의 값(Map)을 타입화하여 Converter가 활용할 수 있도록 구조화
 * @responsibility 모든 값의 타입 정보와 원하는 format(xml, json)으로 변환 시 해당 타입의 필요한 값을 반환
 * @cooperate {
 * input: PsConverter로부터 클라이언트가 필요한 format의 정보 값
 * output: PsConverter에게 자신(노드)을 전달
 * }
 * @see org.psawesome.rsocketmongovue.utils.factory.node
 * @since 20. 7. 4. Saturday
 */

@Data
class PsNode<I> {
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

  private PsNode() { }

  public PsNode(Map<String, Object> param) {
    this.name = propNotNull((String) param.get("name"), "name");
    this.cdata = propNotNull(NODE_STATE.getState(param.get("cdata").toString().toUpperCase()), "cdata");
    this.spell = propNotNull(NODE_STATE.getState(param.get("spell").toString().toUpperCase()), "spell");
    this.attributes = (Map<String, Object>) param.get("attributes");
    this.parent = (String) param.getOrDefault("parent", "");
    this.value = PS_VALUE_FORMAT.classifier(param.get("type").toString().toUpperCase());
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
    private static <I> PsValue<I> classifier(String type) {
      return (PsValue<I>) PS_VALUE_FORMAT.valueOf(type)
              .transform.get();
/*
      return (PsValue<I>)Arrays.stream(PS_VALUE_FORMAT.values())
              .filter(v -> v.type.equalsIgnoreCase(param.get("type").toString().toLowerCase()))
//              .peek(System.out::println)
              .findFirst()
              .orElseThrow(NullPointerException::new)
              .transform.get();
*/
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

    public static NODE_STATE getState(String state) {
      return NODE_STATE.valueOf(state);

/*
      return Arrays.stream(NODE_STATE.values())
//              .peek(System.out::println)
              .filter(v -> v.state.equalsIgnoreCase(state.toString().toLowerCase()))
              .findFirst()
              .orElseThrow(NullPointerException::new)
              ;
*/
    }
  }
}
