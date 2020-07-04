package org.psawesome.rsocketmongovue.utils.factory.form.model;

import lombok.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PsNode {

  private final UUID uuid;
  private final String name;
  private final Map<String, String> attribute;
  private final CDATA_STATE state;

  protected PsNode(String name, Map<String, String> attribute, CDATA_STATE state) {
    this.uuid = UUID.randomUUID();
    this.attribute = this.propNotNull(attribute, "attribute");
    this.name = this.propNotNull(name, "name");
    this.state = this.propNotNull(state, "state");
  }

  protected <T> T propNotNull(T target, String name) {
    return Objects.requireNonNull(target, String.format("must not null node [%s]", name));
  }

  @Getter(AccessLevel.PROTECTED)
  public enum CDATA_STATE {
    ACTIVE("ACTIVE"),
    NONE("NONE");
    private final String state;

    CDATA_STATE(String state) {
      this.state = state;
    }
  }
}
