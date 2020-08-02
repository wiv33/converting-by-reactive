package org.psawesome.router;

import org.psawesome.handler.XmlHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author ps [https://github.com/wiv33/converting-by-reactive]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 8. 3. Monday
 */
@Component
public class XmlRouter {
  private XmlHandler handler;

  public XmlRouter(XmlHandler handler) {
    this.handler = handler;
  }

  @Bean
  public RouterFunction<ServerResponse> xmlRouter() {
    return null;
  }
}
