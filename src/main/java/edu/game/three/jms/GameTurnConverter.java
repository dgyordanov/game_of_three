package edu.game.three.jms;

import edu.game.three.domain.GameTurn;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * JMS message converter for converting messages to GameTurn and the other way around
 *
 * @author Diyan Yordanov
 */
@Component
class GameTurnConverter implements MessageConverter {

    private static final String NUMBER_PROP_NAME = "number";
    private static final String GAME_UUID_PROP_NAME = "gameUUID";

    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        GameTurn gameTurn = (GameTurn) object;
        MapMessage message = session.createMapMessage();
        message.setString("gameUUID", gameTurn.getGameUUID());
        message.setInt(NUMBER_PROP_NAME, gameTurn.getNumber());
        return message;
    }

    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        MapMessage mapMessage = (MapMessage) message;
        return new GameTurn(mapMessage.getString(GAME_UUID_PROP_NAME), mapMessage.getInt(NUMBER_PROP_NAME));
    }

}