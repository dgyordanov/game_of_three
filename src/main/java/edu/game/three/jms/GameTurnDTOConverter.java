package edu.game.three.jms;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * JMS message converter for converting messages to GameTurnDTO and the other way around
 *
 * @author Diyan Yordanov
 */
@Component
public class GameTurnDTOConverter implements MessageConverter {

    private static final String NUMBER_PROP_NAME = "number";
    private static final String GAME_UUID_PROP_NAME = "gameUUID";

    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        GameTurnDTO gameTurnDTO = (GameTurnDTO) object;
        MapMessage message = session.createMapMessage();
        message.setString("gameUUID", gameTurnDTO.getGameUUID());
        message.setInt(NUMBER_PROP_NAME, gameTurnDTO.getNumber());
        return message;
    }

    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        MapMessage mapMessage = (MapMessage) message;
        return new GameTurnDTO(mapMessage.getString(GAME_UUID_PROP_NAME), mapMessage.getInt(NUMBER_PROP_NAME));
    }

}