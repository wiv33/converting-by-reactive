package org.psawesome.rsocketmongovue.domain.conver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * author: ps [https://github.com/wiv33/rsocket-mongo-vue]
 * DATE: 20. 7. 2. Thursday
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("PS_MAPPING")
public class PsMapping {

  @Id
  private String uuid;

  private String user;

  private String docName;

  private String docType;

  // 모든 Elements
  private List<String> elements;

  private LinkedHashMap<String, Object> elementsRelation;

  private Map<String, String> elementsType;

  private LocalDateTime createTime;
}
