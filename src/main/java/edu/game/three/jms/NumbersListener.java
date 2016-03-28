package edu.game.three.jms;

import edu.game.three.domain.GameManager;
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

    @Autowired
    private GameManager gameManager;

    @JmsListener(destination = "${jms.listenOnQueue}")
    public void receiveMessage(Message message) throws JMSException {
        final GameTurn gameTurn = (GameTurn) gameTurnConverter.fromMessage(message);
        System.out.println(String.format("Player: %s - Number: %d - game ID: %s", gameTurn.getPlayerName(),
                gameTurn.getNumber(), gameTurn.getGameUUID()));

        // run async not to block the listener
        new Thread(() -> gameManager.processTurn(gameTurn)).start();
    }
}
