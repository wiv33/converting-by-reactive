package org.psawesome.rsocketmongovue.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author ps [https://github.com/wiv33/rsocket-mongo-vue]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 15. Wednesday
 */

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://psawesome.org", "http://localhost:8081", "http://localhost:8082")
            .allowedMethods("*")
            .maxAge(3700);
  }
}
