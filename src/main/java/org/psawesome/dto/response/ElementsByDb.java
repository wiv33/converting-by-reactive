package org.psawesome.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.psawesome.entity.PsAttribute;
import org.psawesome.entity.PsChild;
import reactor.core.publisher.Flux;

@Getter
@ToString
@EqualsAndHashCode
public class ElementsByDb {
  private final ChildResponse child;  // Element 하나
  private final Flux<AttributeResponse> attributes; // Element에 해당하는 Attributes

  public ElementsByDb(PsChild child, Flux<PsAttribute> attributes) {
    this.child = new ChildResponse(child);
    this.attributes = Flux.from(attributes).map(AttributeResponse::new);
  }
}
