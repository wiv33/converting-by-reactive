package org.psawesome.rsocketmongovue.utils.factory.node;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

public interface NodeMaker {

  default <T, V> Flux<LinkedHashMap<T, V>> init(Stream<LinkedHashMap<T, V>> mapStream) {
    return null;
  }

  default Flux<PsNode<Object>> init(Path path) {
    return Flux.using(() -> Files.lines(path), Flux::fromStream, BaseStream::close)
            .log()
            .reduce(String::concat)
            .map(json -> {
              try {
                return new ObjectMapper().readValue(json, new TypeReference<List<Map<String, Object>>>() {
                }).stream();
              } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
              }
            })
            .flatMapMany(mapStream -> Flux.fromStream(mapStream.map(PsNode::new)))
            .log();
  }

  default <T, V> Flux<LinkedHashMap<T, V>> init(String body) {
    return null;
  }

}
