package org.psawesome.rsocketmongovue.api.rsocket.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 6. 30. Tuesday
 */
@SpringBootTest
class RSocketControllerTest {

  @Autowired
  RSocketRequester requester;

  @Test
  void testPsUserFindAll() {

  }
}