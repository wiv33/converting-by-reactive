package org.psawesome.converter;

import com.thoughtworks.xstream.XStream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;

import static reactor.core.publisher.Mono.when;

public class PsConverterTest {
  @Test
  void testInit() {
    XStream x = new XStream();
    final PsConverter psConverter = new PsConverter();
  }
}
