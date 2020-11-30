package org.psawesome.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("TTB_CMSC_TEMPLATE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PsTemplate {
    @Id
    private Integer templateSeq;

    private String siteCd;

    private String templateId;

    private String descript;

    private String inout;

    private String useYn;

    private String modSeq;

    private LocalDateTime modDt;

    private String regSeq;

    private LocalDateTime regDt;
}
