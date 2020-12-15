package org.psawesome.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.psawesome.entity.PsChild;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Setter(AccessLevel.NONE)
public class ChildResponse implements SetResponse {
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
  private List<String> testList;

  public ChildResponse(PsChild request) {
    accept(request, this);
  }

  public Object getSelectedValue(String key) {
    return getValue(key, this);
  }

}
