package org.psawesome.converter;

import com.thoughtworks.xstream.XStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.dto.response.ElementsByXml;
import org.psawesome.dto.response.ElementsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PsMergingTest {

  @Autowired
  ElementsRepo repo;

  private PsMerging merging;

  @BeforeEach
  void setUp() throws IOException {
    XStream x = new XStream(new LocalXppDriver());
    x.alias("article", ElementsWrapper.class);
//    x.autodetectAnnotations(true);
    x.registerConverter(new ElementsConverter());

    final Path path = Paths.get("data/3_479149.xml");
    final String fileString = Files.readString(path);
    final ElementsWrapper<ElementsByXml> o = (ElementsWrapper<ElementsByXml>) x.fromXML(fileString);
    merging = new PsMerging(repo.selectAllByTemplateSeq(3).block(), o);
  }

  @Test
  void shouldBeNotNull() {
    assertNotNull(merging);
    assertNotNull(merging.response());
  }

  @Test
  void testMerging() {
    merging.response();
  }
}