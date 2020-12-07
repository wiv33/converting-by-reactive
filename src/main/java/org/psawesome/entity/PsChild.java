package org.psawesome.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Table("TTB_CMSC_TEMPLATE_CHILD")
public class PsChild {

  @Id
  private Integer childSeq;
  private String siteCd;
  private String templateSeq;
  private String parameterKey;
  private String childDescript;
  private String parentSeq;
  private Integer sort;
  private String defaultValue;
  private String necYn;
  private String cdataYn;
  private String repeatYn;
  private String remark;
  private String useYn;
  private String modSeq;
  private LocalDateTime modDt;
  private String regSeq;
  private LocalDateTime regDt;

}


