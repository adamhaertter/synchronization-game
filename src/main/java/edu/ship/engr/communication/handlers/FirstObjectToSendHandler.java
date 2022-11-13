package edu.ship.engr.communication.handlers;

import edu.ship.engr.messages.FirstObjectToSend;
import edu.ship.engr.messages.Message;
import edu.ship.engr.messages.ReplyObject;
import edu.ship.engr.peertopeer.PlayRunner;

import java.util.LinkedHashMap;

/**
 * When a FirstMessage is received, we should send out a FirstMessageReply
 * containing the magicNumber in that message
 */
public class FirstObjectToSendHandler implements Handler
{
    @Override
    public void processMessage(Message<?> msgFromJSon)
    {
        FirstObjectToSend msg = new FirstObjectToSend((LinkedHashMap<String, Object>) msgFromJSon.getObject());
        System.out.println("Received first message " + msg);

        PlayRunner.messageAccumulator.queueMessage(new Message<>(new ReplyObject("Got " + msg
                + "!!!!")));
    }
}
