package org.psawesome.rsocketmongovue.domain.transform;

import lombok.*;
import org.psawesome.rsocketmongovue.domain.common.TRANS_TYPE;
import org.psawesome.rsocketmongovue.domain.transform.dto.request.TransformedRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.UUID;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role -수신 메시지: map으로 전달되는 (값이)트리 형식을 원하는 타입의 format으로 변환하라
 * @responsibility TransformedRequest 객체를 받아 데이터를 변환해야 한다.
 * @cooperate {
 * input: 사용자로부터 request
 * output: 사용자에게 response
 * }
 * @see
 * @since 20. 7. 11. Saturday
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Document("Transformed_request")
public class Transformed {

  @Id
  private UUID uuid;

  private Map<String, Object> data;

  private TRANS_TYPE matchType;

  private TRANS_TYPE responseType;

  public Transformed(TransformedRequest request) {
    this.data = request.getData();
    this.matchType = request.getMatchType();
    this.responseType = request.getResponseType();
  }
}
