package org.psawesome.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.psawesome.dto.response.ElementsByXml;
import org.psawesome.dto.response.ElementsWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElementsConverter implements Converter {

  public boolean canConvert(Class clazz) {
    return ElementsWrapper.class.isAssignableFrom(clazz);
  }

  //  Object to XML
  @Override
  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    ElementsWrapper<ElementsByXml> root = (ElementsWrapper<ElementsByXml>) value;

    root.getElements().forEach(el -> {
      for (Map.Entry<String, Object> entry : el.getElements().entrySet()) {

        Object entryValue = entry.getValue();

        if (entryValue instanceof Map) {
          writer.startNode(entry.getKey());
          marshal(entry.getValue(), writer, context);
          writer.endNode();
          continue;
        }

        writer.startNode(entry.getKey());
        if (el.getAttributes().size() > 0) {
          el.getAttributes()
                  .forEach(attrs -> attrs.keySet()
                          .forEach(attr -> writer.addAttribute(attr, attrs.get(attr).toString())));
        }
        writer.setValue(entryValue.toString());
        writer.endNode();
      }
    });
  }

  ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>(List.of(""));

  private void flattenUnmarshal(HierarchicalStreamReader reader, List<ElementsByXml> elements) {

    final List<Map<String, Object>> attrs = this.addAttrs(reader);
    final Map<String, Object> element = Map.of(reader.getNodeName(), reader.getValue());

    elements.add(new ElementsByXml(element, attrs, deque.getLast()));

    while (reader.hasMoreChildren()) {
      deque.add(reader.getNodeName());
      reader.moveDown();
      flattenUnmarshal(reader, elements);
      deque.removeLast();
      reader.moveUp();
    }

  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    ElementsWrapper<ElementsByXml> result = new ElementsWrapper<>(new ArrayList<>());
    final List<ElementsByXml> elements = result.getElements();

    // 재귀 시작
    flattenUnmarshal(reader, elements);

    return result;
  }

  private List<Map<String, Object>> addAttrs(HierarchicalStreamReader reader) {
    if (reader.getAttributeCount() <= 0) {
      return List.of();
    }

//    현재 xml 노드의 attribute를 Map List로 변환
    return Stream.iterate(0, i -> i + 1)
            .limit(reader.getAttributeCount())
            .map(i -> Map.of(reader.getAttributeName(i), (Object) reader.getAttribute(i)))
            .collect(Collectors.toList());
  }
}