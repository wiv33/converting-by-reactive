package org.psawesome.converter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.psawesome.dto.response.ElementsByXml;
import org.psawesome.dto.response.ElementsWrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PsConverterTest {

  @Test
  void testMap() throws IOException {
    XStream x = new XStream(new LocalXppDriver());
    x.alias("article", Map.class);
//    x.autodetectAnnotations(true);
    x.registerConverter(new MapEntryConverter());

    final Path path = Paths.get("data/3_479149.xml");
    final String s = Files.readString(path);
    final Object o = x.fromXML(s, Map.class);
    System.out.println("o = " + o);
  }

  @Test
  void testElementsWrapper() throws IOException {
    XStream x = new XStream(new LocalXppDriver());
    x.alias("article", ElementsWrapper.class);
//    x.autodetectAnnotations(true);
    x.registerConverter(new ElementsConverter());

    final Path path = Paths.get("data/3_479149.xml");
    final String s = Files.readString(path);
    final ElementsWrapper<ElementsByXml> o = (ElementsWrapper<ElementsByXml>) x.fromXML(s);

    o.getElements()
            .forEach(d -> d.getElements()
                    .forEach((key, value) ->
                            System.out.printf("element name: [%s], element value: [%s]\nattributes: [%s]\n*:*: %s :*:*\n\n", key, value, d.getAttributes().toString(), d.getParentNode())));

    Assertions.assertAll(
            () -> assertNotEquals(0, o.getElements().size()),
            () -> assertEquals("", o.getElements().get(0).getParentNode()),
            () -> assertEquals("article", o.getElements().get(1).getParentNode()),
            () -> assertEquals("3", o.getElements().get(1).getElements().get("media_code"))
    );
  }

  @Test
  void testMapToXml() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("name", "chris");
    map.put("island", "faranga");
    Map<String, Object> map2 = new HashMap<String, Object>();
    map2.put("test", "테스트_");
    Map<String, Object> map3 = new HashMap<String, Object>();
    map3.put("national", "_korea");
    List<ElementsByXml> xmls = List.of(new ElementsByXml(map,
                    List.of(), ""),
            new ElementsByXml(map2,
                    List.of(Map.of("href", "http://localhost:9090",
                            "caption_content", "드라마 〈18 어게인〉_. ")
                    ), ""),
            new ElementsByXml(map3,
                    List.of(Map.of("href", "http://localhost:9090",
                            "$caption_content", "드라마 〈18 어게인〉.__")
                    ), ""));

//    XmlFriendlyNameCoder nameCoder = new XmlFriendlyNameCoder("__", "_");
    XStream x = new XStream(new LocalXppDriver());

    x.alias("root", ElementsWrapper.class);
    x.registerConverter(new ElementsConverter());

    String xml = x.toXML(new ElementsWrapper<>(xmls));
    System.out.println(xml);
  }


}

class MapEntryConverter implements Converter {

  public boolean canConvert(Class clazz) {
    return AbstractMap.class.isAssignableFrom(clazz);
  }

  //  Object to XML
  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    AbstractMap map = (AbstractMap) value;

    for (Object obj : map.entrySet()) {
      Map.Entry entry = (Map.Entry) obj;
      Object entryValue = entry.getValue();

      if (entryValue instanceof Map) {
        writer.startNode(entry.getKey().toString());
        marshal(entry.getValue(), writer, context);
        writer.endNode();
        continue;
      }

      writer.startNode(entry.getKey().toString());
      writer.setValue(entryValue.toString());
      writer.endNode();
    }
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    Map<String, Object> map = new HashMap<>();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (reader.hasMoreChildren()) {
        Map<String, Object> childMap = new HashMap<>();
        map.put(reader.getNodeName(), childMap);
        unmarshalHierarchical(reader, context, childMap);
        reader.moveUp();
        continue;
      }
      map.put(reader.getNodeName(), reader.getValue());
      reader.moveUp();
    }
    return map;
  }

  private void unmarshalHierarchical(HierarchicalStreamReader reader, UnmarshallingContext context, Map<String, Object> map) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (reader.hasMoreChildren()) {
        Map<String, Object> childMap = new HashMap<>();
        map.put(reader.getNodeName(), childMap);
        unmarshalHierarchical(reader, context, childMap);
        reader.moveUp();
        continue;
      }
      map.put(reader.getNodeName(), reader.getValue());
      reader.moveUp();
    }
  }
}