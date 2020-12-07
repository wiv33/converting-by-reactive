package org.psawesome.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.entity.PsAttribute;

import static org.junit.jupiter.api.Assertions.*;

class AttributeResponseTest {

  PsAttribute request;

  @BeforeEach
  void setUp() {
    request = PsAttribute.builder()
            .attributeKey("ps")
            .attributeRemark("rm")
            .attributeSeq(3)
            .attributeValue("awesome")
            .build();
  }

  @Test
  void shouldBeEqualsRequestAndResponse() {
    AttributeResponse response = new AttributeResponse(request);

    assertAll(
            () -> Assertions.assertEquals(request.getAttributeKey(), response.getAttributeKey()),
            () -> Assertions.assertEquals(request.getAttributeRemark(), response.getAttributeRemark()),
            () -> Assertions.assertEquals(request.getAttributeSeq(), response.getAttributeSeq()),
            () -> Assertions.assertEquals(request.getAttributeValue(), response.getAttributeValue())
    );
    ;
  }
}