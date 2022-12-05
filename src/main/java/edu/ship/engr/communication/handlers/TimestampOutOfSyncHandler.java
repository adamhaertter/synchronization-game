package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.messages.Message;
import edu.ship.engr.messages.TimestampOutOfSyncReply;

import java.util.LinkedHashMap;

public class TimestampOutOfSyncHandler implements Handler {

    @Override
    public void processMessage(Message<?> msg) {
        TimestampOutOfSyncReply reply = new TimestampOutOfSyncReply((LinkedHashMap<String, Object>) msg.getObject());
        // the fact that we get a BadMovementReply is enough to signal that we can't move to the spot we want to

        Timestamp.getInstance().setTimestamp(reply.getTimestamp());
    }


}
