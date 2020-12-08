package org.psawesome.converter;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

import java.io.Writer;
import java.util.regex.Pattern;

/**
 * XStream(http://xstream.codehaus.org/)을 사용하는 확장 PrettyPrintWriter클래스
 *
 * @since 2008.05.30
 */
public class LocalPrettyPrintWriter extends PrettyPrintWriter {
  private static final char[] AMP = "&amp;".toCharArray();
  private static final char[] LT = "<".toCharArray();
  private static final char[] GT = ">".toCharArray();
  private static final char[] SLASH_R = " ".toCharArray();
  private static final char[] QUOT = "&quot;".toCharArray();
  private static final char[] APOS = "&apos;".toCharArray();

  public LocalPrettyPrintWriter(Writer writer) {
    super(writer);
  }

  /**
   * 실제 text값을 xml태그를 통해 표현하는 메소드이다.
   * 상위 클래스인 PrettyPrintWriter의 다른 기능을 그대로 사용하되 CDATA처리를 위해 오버라이딩했다. .
   */
  protected void writeText(QuickWriter writer, String text) {
    /**
     *  값이 없을 경우 <태그 /> 형태로 그냥 반환해버린다.
     *  차후 값이 없더라도 CDATA로 싸서 공백을 반환해야 하는 경우 아래 조건을 없애면 된다.
     */
    if (text.trim().length() < 1) {
      super.writeText(writer, text);
      return;
    }

    String CDATAPrefix = "<![CDATA[";
    String CDATASuffix = "]]>";

    /**
     *  실제 CDATA를 추가해주는 부분
     *  CDATA에 대한 추가 로직이 필요하거나 CDATA사용이 필요없을 경우에는 아래 부분을 제거하지 말고 상위 클래스 사용을 권장한다.
     */
    if (!text.startsWith(CDATAPrefix) && !Pattern.matches("[^[0-9]]+", text)) {
      text = CDATAPrefix + text + CDATASuffix;
    }

    int length = text.length();
    if (!text.startsWith(CDATAPrefix)) {
      for (int i = 0; i < length; i++) {
        char c = text.charAt(i);
        switch (c) {
          case '&':
            writer.write(AMP);
            break;
          case '<':
            writer.write(LT);
            break;
          case '>':
            writer.write(GT);
            break;
          case '"':
            writer.write(QUOT);
            break;
          case '\'':
            writer.write(APOS);
            break;
          case '\r':
            writer.write(SLASH_R);
            break;
          default:
            writer.write(c);
        }
      }
    } else {
      for (int i = 0; i < length; i++) {
        char c = text.charAt(i);
        writer.write(c);
      }
    }
  }
}
