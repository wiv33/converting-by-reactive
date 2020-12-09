package org.psawesome.converter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

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