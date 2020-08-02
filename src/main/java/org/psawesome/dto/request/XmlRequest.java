package org.psawesome.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author ps [https://github.com/wiv33/converting-by-reactive]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 8. 3. Monday
 */
@Builder @Data
public class XmlRequest {
  private String id;
  private List<Map<String, Object>> body;
}
