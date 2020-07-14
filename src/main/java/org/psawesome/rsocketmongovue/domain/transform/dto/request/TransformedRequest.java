package org.psawesome.rsocketmongovue.domain.transform.dto.request;

import lombok.Builder;
import lombok.Data;
import org.psawesome.rsocketmongovue.domain.common.TRANS_TYPE;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
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
@Data
@Builder
@Document("transformed_request")
public class TransformedRequest {

  @Id
  private final String id;
  private final List<Map<String, Object>> data;
  private final TRANS_TYPE matchType;
  private final TRANS_TYPE responseType;
}
