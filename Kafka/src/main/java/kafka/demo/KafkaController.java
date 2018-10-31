package kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author huangzehui
 * @date 18-10-29 上午10:16
 */
@RestController
public class KafkaController {
    private Logger logger = LoggerFactory.getLogger(KafkaController.class);
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${application.topic}")
    private String  topic;

    @RequestMapping(value = "/kafka/send", method = RequestMethod.GET)
    @CrossOrigin
    public void send(String data){
        logger.info("发送数据："+data);
        kafkaTemplate.send(topic,data);
    }



    @KafkaListener(topics = "${application.topic}")
    public void receiveMessage(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        try {
            if (kafkaMessage.isPresent()) {
                String data=kafkaMessage.get().toString();
                logger.info("消费数据："+data);
                logger.info("消费成功");
            }
        }catch (Exception e){
            logger.error("消息消费失败：{}",e);
        }
    }
}
