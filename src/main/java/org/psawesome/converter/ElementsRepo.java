package org.psawesome.converter;

import lombok.Getter;
import org.psawesome.dto.response.ElementsByDb;
import org.psawesome.entity.PsAttribute;
import org.psawesome.entity.PsChild;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
@Getter
public class ElementsRepo {

  private final DatabaseClient client;

  public ElementsRepo(DatabaseClient.Builder databaseClientBuilder) {
    this.client = databaseClientBuilder.build();
  }

  public Flux<ElementsByDb> selectAllByTemplateSeq(Integer templateSeq) {
    return this.client
            .select()
            .from(PsChild.class)
            .matching(where("TEMPLATE_SEQ").is(templateSeq).and(where("USE_YN").is("Y")))
            .as(PsChild.class)
            .all()
            .log()
            .map(psChild -> new ElementsByDb(psChild, this.client.select()
                    .from(PsAttribute.class).matching(where("CHILD_SEQ").is(psChild.getChildSeq()))
                    .as(PsAttribute.class)
                    .all()))
            ;
  }


}
