package org.psawesome.rsocketmongovue.utils.factory.node;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

public interface NodeMaker {

  default <T, V> Flux<LinkedHashMap<T, V>> init(Stream<LinkedHashMap<T, V>> mapStream) {
    return null;
  }

  default <T, V> Mono<LinkedHashMap<T, V>> init(Path path) {
    return Flux.using(() -> Files.lines(path), Flux::fromStream, BaseStream::close)
            .reduce(String::concat)
            .log()
            .map(item -> {
              try {
                return new ObjectMapper().readValue(item, new TypeReference<List<LinkedHashMap<T, V>>>() {
                }).stream();
              } catch (JsonProcessingException e) {
                throw new NullPointerException();
              }
            })
            .flatMap(mapStream -> Flux.fromStream(mapStream))
            .map(s )
            .log();
  }

  default <T, V> Flux<LinkedHashMap<T, V>> init(String body) {
    return null;
  }

}
