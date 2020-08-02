package org.psawesome.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.reactivestreams.Publisher;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author ps [https://github.com/wiv33/converting-by-reactive]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 7. 22. Wednesday
 */
public class KafkaManager {
  final Map<String, Object> consumerProperties;
  final Map<String, Object> producerProperties;

  public KafkaManager(String topic) {
    final EmbeddedKafkaBroker broker = new EmbeddedKafkaBroker(1, false, 1, topic);
    broker.afterPropertiesSet();

    final String bootstrapServers = broker.getBrokersAsString();

    consumerProperties = Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
            ConsumerConfig.CLIENT_ID_CONFIG, "ps-consumer",
            ConsumerConfig.GROUP_ID_CONFIG, "awesome",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
    );

    producerProperties = Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
            ProducerConfig.CLIENT_ID_CONFIG, "ps-producer",
            ProducerConfig.ACKS_CONFIG, "all",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
    );

  }

  public Flux<SenderResult<String>> producer(final Publisher<? extends SenderRecord<String, String, String>> publisher) {
    return KafkaSender.create(SenderOptions.<String, String>create(consumerProperties))
            .send(publisher);
  }

  public Flux<ReceiverRecord<String, String>> consumer(final String topic) {
    ReceiverOptions<String, String> options = ReceiverOptions.<String, String>create(consumerProperties)
            .subscription(Collections.singletonList(topic));
    return KafkaReceiver.create(options)
            .receive();
  }
}
