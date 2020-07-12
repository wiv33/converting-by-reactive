package org.psawesome.rsocketmongovue.domain.transform;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.psawesome.rsocketmongovue.domain.transform.dto.request.TransformedRequest;

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
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Transformed {

  public Transformed(TransformedRequest request) {

  }
}
