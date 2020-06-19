package org.psawesome.rsocketmongovue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RsocketMongoVueApplication {

  public static void main(String[] args) {
    SpringApplication.run(RsocketMongoVueApplication.class, args);
  }




/*
  @Bean
  RSocket rSocketRequester() {
    return RSocketConnector
            .create()
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .payloadDecoder(DefaultConnectionSetupPayload::new)
            .connect(TcpClientTransport.create(7000))
            .block()
            ;
  }

  @Bean
  public RSocketRequester requester() {
    return RSocketRequester.builder()
            .connectTcp("localhost", 7000)
            .block();
  }
*/

}
