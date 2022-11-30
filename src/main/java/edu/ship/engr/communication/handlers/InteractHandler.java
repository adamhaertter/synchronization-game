package edu.ship.engr.communication.handlers;

import edu.ship.engr.display.Board;
import edu.ship.engr.display.GUI;
import edu.ship.engr.display.entity.Item;
import edu.ship.engr.display.entity.Player;
import edu.ship.engr.messages.InteractMessage;
import edu.ship.engr.messages.Message;

import java.util.LinkedHashMap;

public class InteractHandler implements Handler {
    @Override
    public void processMessage(Message<?> msgFromJSon) {
        System.out.println("Message as follows: " + msgFromJSon);
        InteractMessage interact = new InteractMessage((LinkedHashMap<String, Object>) msgFromJSon.getObject());
        System.out.println("Received interact for the other player with object: " + interact.getTargetX() + ", " + interact.getTargetY());

        Board board = Board.getInstance();
        Player otherPlayer = board.getOtherPlayer();

        GUI.processInteraction(otherPlayer, interact.getTargetX(), interact.getTargetY());


    }
}
