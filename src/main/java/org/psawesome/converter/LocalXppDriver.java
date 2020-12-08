package org.psawesome.converter;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;

/**
 * XStream(http://xstream.codehaus.org/)을 사용하는 확장 XppDriver클래스
 *
 * @since 2008.05.30
 */
public class LocalXppDriver extends XppDriver {

  public HierarchicalStreamWriter createWriter(Writer out) {
    return new LocalPrettyPrintWriter(out);
  }
}
