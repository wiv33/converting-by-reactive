package org.psawesome.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.entity.PsChild;

import java.util.List;

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
            .testList(List.of("a","b", "c"))
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

  @Test
  void testGetSelectedValue() {
//    response == model @Xml
    assertEquals("어머나", response.getSelectedValue("childDescript"));
    assertEquals("ㅇㅇㅇ", response.getSelectedValue("defaultValue"));
    assertEquals("N", response.getSelectedValue("repeatYn"));
    assertArrayEquals(List.of("a", "b", "c").toArray(), ((List) response.getSelectedValue("testList")).toArray());

    final List<String> testList = (List<String>)response.getSelectedValue("testList");
    testList.forEach(System.out::println);
  }

  @Test
  void testNoSuchFieldIsNull() {
    assertNull(response.getSelectedValue("ps"));
  }
}