package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.handlers.Handler;
import edu.ship.engr.messages.InteractMessage;
import edu.ship.engr.messages.Message;
import edu.ship.engr.messages.MovementMessage;

import java.util.LinkedHashMap;

public class InteractHandler implements Handler {
    @Override
    public void processMessage(Message<?> msgFromJSon) {
        InteractMessage interact = new InteractMessage((LinkedHashMap<String, Object>) msgFromJSon.getObject());
        System.out.println("Received interact for the other player with object: " + interact.getEventObject());
    }
}
