package org.psawesome.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ElementsByXml {
  private final Map<String, Object> elements;
  private final List<Map<String, Object>> attributes;
  private final String parentNode;
}
