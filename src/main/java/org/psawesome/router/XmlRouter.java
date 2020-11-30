package org.psawesome.router;

import org.psawesome.handler.XmlHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

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
  private final XmlHandler handler;

  public XmlRouter(XmlHandler handler) {
    this.handler = handler;
  }

  @Bean
  public RouterFunction<ServerResponse> xmlRouterMethod() {
    return route(GET("/"), request -> ServerResponse.ok().build());
  }
}
