package org.psawesome.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.psawesome.dto.request.XmlRequest;
import org.psawesome.router.XmlRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

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
//@ExtendWith(SpringExtension.class)
@WebFluxTest
@AutoConfigurationPackage(basePackageClasses = {XmlRouter.class, XmlHandler.class})
@AutoConfigureWebTestClient
@AutoConfigureWebFlux
public class XmlHandlerTest {

  private WebTestClient testClient;

  @Autowired
  XmlRouter xmlRouter;

  @Autowired
  XmlHandler handler;

  List<Map<String, Object>> body;

  @BeforeEach
  void setUp() throws IOException {
    testClient = WebTestClient.bindToRouterFunction(xmlRouter.xmlRouter())
            .build();
    final ObjectMapper mapper = new ObjectMapper();
    body = mapper.readValue(Files.readString(Paths.get("input-one-depth.json")),
            new TypeReference<>() {
            });
  }

  @Test
  void testHandler() {
    final XmlRequest psawesome = XmlRequest.builder()
            .id("psawesome")
            .body(body)
            .build();

    Assertions.assertNotNull(psawesome.getBody());

  }
}
