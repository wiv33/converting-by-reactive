package org.psawesome.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("TTB_CMSC_TEMPLATE_ATTRIBUTE")
public class PsAttribute {

  @Id
  private Integer attributeSeq;
  private String siteCd;
  private String childSeq;
  private String attributeKey;
  private String attributeValue;
  private String attributeRemark;
  private String modSeq;
  private LocalDateTime modDt;
  private String regSeq;
  private LocalDateTime regDt;
}


