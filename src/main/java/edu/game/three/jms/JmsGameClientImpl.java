package edu.game.three.jms;

import edu.game.three.domain.GameTurn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * JMS game client implementation
 *
 * @author Diyan Yordanov
 */
@Component
public class JmsGameClientImpl implements GameClient {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${jms.sendToQueue}")
    private String sendTo;

    @Override
    public void sendNextNumber(GameTurn gameTurn) {
        jmsTemplate.convertAndSend(sendTo, gameTurn);
        System.out.println("Sent via JMS <" + gameTurn + ">");
    }
}
