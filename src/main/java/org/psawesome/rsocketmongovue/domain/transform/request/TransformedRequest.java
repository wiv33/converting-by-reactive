package org.psawesome.rsocketmongovue.domain.transform.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role - 수신 메시지: from을 to로 변환하라
 * @responsibility 메시지를 Transformed에게 전달하는 버스
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 11. Saturday
 */
@RequiredArgsConstructor
@Getter
@ToString @EqualsAndHashCode
public class TransformedRequest {
  private final Map<String, Object> from;
  // String으로 받을 수 있는지 명확하지가 않음
  private final TRANSFORMED_TYPE to;

  public enum TRANSFORMED_TYPE {
    TEXT, JSON, XML
  }
}
