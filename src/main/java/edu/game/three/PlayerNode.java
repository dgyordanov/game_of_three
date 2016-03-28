package edu.game.three;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

import java.io.Serializable;

@SpringBootApplication
@EnableJms
public class PlayerNode implements Serializable {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PlayerNode.class, args);
    }
}

