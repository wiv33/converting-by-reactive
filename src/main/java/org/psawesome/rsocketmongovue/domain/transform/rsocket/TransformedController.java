package org.psawesome.rsocketmongovue.domain.transform.rsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * package: org.psawesome.rsocketmongovue.domain.transform.rsocket
 * author: PS
 * DATE: 2020-07-12 일요일 04:57
 */
@Controller
public class TransformedController {

  // 여기엔... transformed가능한 리스트 출력
  // 이 정도는 http에서 해도 되지 않을까?
  @MessageMapping("transformed.help")
  public Mono<?> transformedAll() {
    return Mono.just(List.of("TEXT", "JSON", "XML"))
            .log();
  }
}
