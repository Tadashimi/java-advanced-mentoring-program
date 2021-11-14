package com.epam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msg")
public class Homework4part2Controller {

    @Autowired
    private KafkaProducer producer;

    @PostMapping
    public void sendMessage(String msg) {
        producer.sendMessage(msg);
    }
}
