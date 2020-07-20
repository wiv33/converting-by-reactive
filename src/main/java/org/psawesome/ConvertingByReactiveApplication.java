package org.psawesome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConvertingByReactiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConvertingByReactiveApplication.class, args);
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
*/

/*
  @Bean
  public RSocketRequester requester() {
    return RSocketRequester.builder()
            .connectTcp("localhost", 7000)
            .block();
  }
*/
/*

  final RSocketRequester.Builder builder;

  @Bean
  public RSocketRequester requester() {
    return builder
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
//            .setupRoute("", "")
            .connectTcp("localhost", 7000)
            .block()
            ;
  }
*/

}
