package org.psawesome.converter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.psawesome.dto.response.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter(AccessLevel.NONE)
@RequiredArgsConstructor
public class PsMerging {
  private final ElementsWrapper<ElementsByDb> dbWrapper;
  private final ElementsWrapper<ElementsByXml> articleWrapper;

  public ElementsWrapper<ElementsByMerge> response() {
//    DB 기준 사용할 Key 모음
    Set<String> parameterKeyList = this.articleWrapper.getElements()
            .stream().flatMap(map -> map.getElements().keySet().stream())
            .collect(Collectors.toSet());

//    활용할 데이터
  this.articleWrapper.getElements()
            .stream()
            .filter(f -> parameterKeyList.stream().anyMatch(innerF -> f.getElements().containsKey(innerF)))
            .map(data -> {
              this.dbWrapper.getElements()
                      .stream()
                      .filter(f -> data.getElements().containsKey(f.getChild().getParameterKey()));
              return data;
            });

    this.dbWrapper.getElements().stream()
            .filter(f -> this.articleWrapper.getElements()
                    .stream()
                    .anyMatch(byXml -> byXml.getElements().containsKey(f.getChild().getParameterKey())))
            .flatMap(d -> {
              final ChildResponse child = d.getChild();
              child.getParameterKey();
              child.getDefaultValue();
              child.getRepeatYn();
              child.getSort();
              System.out.println("child = " + child);

              if (child.getRepeatYn().equalsIgnoreCase("Y")) {
                return Stream.of(new ElementsByMerge(
                        Map.of("", ""),
                        Map.of("", List.of())
                ));
              }

              return Stream.of(new ElementsByMerge(
                      Map.of(child.getParameterKey(), child.getDefaultValue()),
                      Map.of("", List.of(Map.of()))));
            })
            .collect(Collectors.toList())
    ;
    return new ElementsWrapper<>(List.of());
  }

}
