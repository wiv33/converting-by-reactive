package org.psawesome.rsocketmongovue.domain.doc.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class PsArticleTest {

  WebClient client;
  RestTemplate restTemplate;



  @BeforeEach
  void setUp() {
    String baseUrl = "http://joins.elasticsearch";
    client = WebClient.builder()
            .baseUrl(baseUrl).build();
    restTemplate = new RestTemplateBuilder()
//            .rootUri(baseUrl)
            .build();
  }

  @Test
  void testClientNotNull() {
    client.get()
    .retrieve()
    .bodyToFlux(String.class)
    .log()
    .subscribe(System.out::println);
  }

  @Test
  void testRestTemplate() throws IOException {
    ResponseEntity<String> forEntity = restTemplate.getForEntity("http://joins.elasticsearch/article/_search?", String.class);
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> stringListMap = mapper.readValue(forEntity.getBody(), new TypeReference<>() {
    });

    LinkedHashMap<String, Object> hits = (LinkedHashMap<String, Object>) stringListMap.get("hits");
    List<Map<String, Object>> hits1 = (List<Map<String, Object>>) hits.get("hits");

    makeJson(forEntity);

    hits1.forEach(map -> {

    });



  }

  private void makeJson(ResponseEntity<String> forEntity) throws IOException {
    var str = "C:\\private\\projects\\rsocket-mongo-vue\\src\\test\\java\\org\\psawesome\\rsocketmongovue\\domain\\doc\\entity\\json";
    File file = new File(str);
    if (!file.exists()) {
      file.mkdirs();
    }
    File file1 = new File(file, "result.json");
    if (!file1.exists()) {
      file1.createNewFile();
    }
    file1.setWritable(true, false);
    FileWriter fw = new FileWriter(file1, StandardCharsets.UTF_8);
    fw.write(forEntity.getBody());
    fw.flush();
    fw.close();
  }


}