package org.psawesome.converter;

import com.thoughtworks.xstream.XStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.psawesome.dto.response.ElementsByXml;
import org.psawesome.dto.response.ElementsWrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ElementsConverterTest {

  @Test
  void testElementsWrapper() throws IOException {
    XStream x = new XStream(new LocalXppDriver());
    x.alias("article", ElementsWrapper.class);
//    x.autodetectAnnotations(true);
    x.registerConverter(new ElementsConverter());

    final Path path = Paths.get("data/3_479149.xml");
    final String fileString = Files.readString(path);
    final ElementsWrapper<ElementsByXml> o = (ElementsWrapper<ElementsByXml>) x.fromXML(fileString);

    final var attributes = o.getElements()
            .stream()
            .filter(s -> s.getParentNode().equals("multi_items"))
            .map(ElementsByXml::getAttributes)
            .collect(toList());

    final var elementEntrySet = o.getElements()
            .stream().filter(d -> d.getParentNode().equals("multi_items"))
            .map(ElementsByXml::getElements)
            .collect(toList());

    final var itemsEntries = o.getElements().stream()
            .filter(s -> s.getElements().get("item") != null)
            .map(ElementsByXml::getElements)
            .collect(toList());

    final var itemsAttributes = o.getElements().stream()
            .filter(s -> s.getElements().get("item") != null)
            .map(ElementsByXml::getAttributes)
            .collect(toList());

/*
    o.getElements()
            .forEach(d -> d.getElements()
                    .forEach((key, value) ->
                            System.out.printf("element name: [%s], element value: [%s]\nattributes: [%s]\n*:*: %s :*:*\n\n", key, value, d.getAttributes().toString(), d.getParentNode())));
*/

    Assertions.assertEquals(61, o.getElements().size());

    Assertions.assertAll(
            () -> assertNotEquals(0, o.getElements().size()),
            () -> assertEquals("", o.getElements().get(0).getParentNode()),
            () -> assertEquals("article", o.getElements().get(1).getParentNode()),
            () -> assertEquals("3", o.getElements().get(1).getElements().get("media_code"))
    );
  }

  @Test
  void testElementsByXmlToXml() {
    Map<String, Object> map = new HashMap<>();
    map.put("name", "chris");
    map.put("island", "faranga");
    Map<String, Object> map2 = new HashMap<>();
    map2.put("test", "테스트_");
    Map<String, Object> map3 = new HashMap<>();
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