package org.psawesome.rsocketmongovue.domain.transform.dto.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.psawesome.rsocketmongovue.domain.common.TRANS_TYPE;

import java.util.Map;
import java.util.UUID;

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
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class TransformedRequest {
  private Map<String, Object> data;
  private TRANS_TYPE matchType;
  private TRANS_TYPE responseType;
}
