package org.psawesome.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.entity.PsChild;

import static org.junit.jupiter.api.Assertions.*;

class ChildResponseTest {

  ChildResponse response;
  PsChild psChild;

  @BeforeEach
  void setUp() {
    psChild = PsChild.builder().childSeq(1)
            .cdataYn("Y")
            .childDescript("어머나")
            .defaultValue("ㅇㅇㅇ")
            .repeatYn("N")
            .build();
    response = new ChildResponse(psChild);
  }

  @Test
  void testNotNullValues() {
    assertEquals(psChild.getCdataYn(), response.getCdataYn());
    assertEquals(psChild.getChildDescript(), response.getChildDescript());
    assertEquals(psChild.getDefaultValue(), response.getDefaultValue());
    assertEquals(psChild.getRepeatYn(), response.getRepeatYn());
  }
}