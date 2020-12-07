package org.psawesome.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.psawesome.entity.PsAttribute;

@Data
@Setter(AccessLevel.NONE)
public class AttributeResponse implements SetResponse {
  private Integer attributeSeq;
  private String siteCd;
  private String childSeq;
  private String attributeKey;
  private String attributeValue;
  private String attributeRemark;

  public AttributeResponse(PsAttribute request) {
    accept(request, this);
  }
}
