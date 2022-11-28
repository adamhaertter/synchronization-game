package edu.ship.engr.communication.handlers;

import edu.ship.engr.display.Board;
import edu.ship.engr.display.entity.Item;
import edu.ship.engr.messages.InteractMessage;
import edu.ship.engr.messages.Message;

import java.util.LinkedHashMap;

public class InteractHandler implements Handler {
    @Override
    public void processMessage(Message<?> msgFromJSon) {
        InteractMessage interact = new InteractMessage((LinkedHashMap<String, Object>) msgFromJSon.getObject());
        System.out.println("Received interact for the other player with object: " + interact.getTargetX() + ", " + interact.getTargetY());

        Board board = Board.getInstance();
        Item item = board.getItemAt(interact.getTargetX(), interact.getTargetY());
        item.interact(board.getMyPlayer());
    }
}
