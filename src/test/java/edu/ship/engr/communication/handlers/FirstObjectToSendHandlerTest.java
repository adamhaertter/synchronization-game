package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.TestUtilities;
import edu.ship.engr.messages.FirstObjectToSend;
import edu.ship.engr.messages.ReplyObject;
import edu.ship.engr.messages.Message;
import edu.ship.engr.peertopeer.PlayRunner;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstObjectToSendHandlerTest
{

    @Test
    public void queuesResponse() throws IOException
    {
        // set up the message and then convert it to simulate what happens when
        // we sent it
        FirstObjectToSend msgToProcess = new FirstObjectToSend(55,77);
        Message<FirstObjectToSend> msgToSend = new Message<>(msgToProcess);
        Message<?> msgAfterSending = TestUtilities.convertToUnpackedJSon(msgToSend);

        FirstObjectToSendHandler handler = new FirstObjectToSendHandler();
        handler.processMessage(msgAfterSending);

        // A FirstMessageReply should have been queued
        ArrayList<Message<?>> queuedMessages =
                PlayRunner.messageAccumulator.getPendingMsgs();
        assertEquals(1,queuedMessages.size());
        assertEquals(ReplyObject.class,queuedMessages.get(0).getObjectType() );
    }
}
