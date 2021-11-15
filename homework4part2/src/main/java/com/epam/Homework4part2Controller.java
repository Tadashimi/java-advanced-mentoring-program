package com.epam;

import com.epam.schemas.KafkaConsumerNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msg")
public class Homework4part2Controller {

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private KafkaConsumerNew consumerNew;

    @PostMapping
    public void sendMessage(@RequestParam String schemaId, @RequestBody String msg) {
        producer.sendMessage(schemaId, msg);
    }

    @GetMapping
    public String getMessages() {
        return consumerNew.readMessages();
    }
}
