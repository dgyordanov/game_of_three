package edu.game.three.domain;


import edu.game.three.PlayerNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PlayerNode.class)
public class IntegrationTest {

    public static final String TEST_REMOTE_PLAYER = "test remote player";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${jms.listenOnQueue}")
    private String gameNodeListenDest;

    @Value("${jms.sendToQueue}")
    private String gameNodeSendDest;

    @Value("${player.name}")
    private String playerName;

    @Test
    public void testStartGame() throws JMSException {
        String uuid = UUID.randomUUID().toString();
        jmsTemplate.convertAndSend(gameNodeListenDest, new GameTurn(uuid, 20, TEST_REMOTE_PLAYER));

        GameTurn turn = (GameTurn) jmsTemplate.receiveAndConvert(gameNodeSendDest);
        assertThat(turn.getNumber(), equalTo(7));
        assertThat(turn.getGameUUID(), equalTo(uuid));
        assertThat(turn.getPlayerName(), equalTo(playerName));
    }

    @Test
    public void wholeGame() {
        String uuid = UUID.randomUUID().toString();

        jmsTemplate.convertAndSend(gameNodeListenDest, new GameTurn(uuid, 56, TEST_REMOTE_PLAYER));
        GameTurn turn1 = (GameTurn) jmsTemplate.receiveAndConvert(gameNodeSendDest);
        assertThat(turn1.getNumber(), equalTo(19));

        jmsTemplate.convertAndSend(gameNodeListenDest, new GameTurn(uuid, 6, TEST_REMOTE_PLAYER));
        GameTurn turn2 = (GameTurn) jmsTemplate.receiveAndConvert(gameNodeSendDest);
        assertThat(turn2.getNumber(), equalTo(2));

    }


}
