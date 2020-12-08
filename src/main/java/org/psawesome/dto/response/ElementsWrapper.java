package org.psawesome.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ElementsWrapper<T> {
  private final List<T> elements;
}
