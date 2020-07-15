package org.psawesome.rsocketmongovue.domain.transform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.psawesome.rsocketmongovue.domain.node.model.NodeMaker;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransformedResponse implements NodeMaker{
  private String id;
  private List<Map<String, Object>> data;
}
