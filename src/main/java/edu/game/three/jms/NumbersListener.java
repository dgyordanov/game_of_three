package edu.game.three.jms;

import edu.game.three.domain.GameTurn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * JMS listener which listen for numbers from the other player and send them fo processing to the domain logic
 *
 * @author Diyan Yordanov
 */
@Component
public class NumbersListener {

    @Autowired
    private GameTurnConverter gameTurnConverter;

    @JmsListener(destination = "${jms.listenOnQueue}")
    public void receiveMessage(Message message) throws JMSException {
        // TODO: Configure the listener to use this adapter
        GameTurn gameTurn = (GameTurn) gameTurnConverter.fromMessage(message);
        System.out.println("Received <" + gameTurn + ">");

    }
}
