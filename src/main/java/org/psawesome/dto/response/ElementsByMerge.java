package org.psawesome.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ElementsByMerge {
  private final Map<String, Object> elementInfo;
  private final Map<String, List<Map<String, Object>>> attributeInfo;
}
