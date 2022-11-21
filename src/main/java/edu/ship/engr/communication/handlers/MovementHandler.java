package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.Board;
import edu.ship.engr.messages.Message;
import edu.ship.engr.messages.ReplyObject;
import edu.ship.engr.peertopeer.PlayRunner;
import edu.ship.engr.messages.MovementMessage;

import java.util.LinkedHashMap;

public class MovementHandler implements Handler {

    @Override
    public void processMessage(Message<?> msgFromJSon) {
        MovementMessage movement = new MovementMessage((LinkedHashMap<String, Object>) msgFromJSon.getObject());
        System.out.println("Received movement for the other player with coordinates x" + movement.getNewX() + " y" + movement.getNewY());


        if (Timestamp.getInstance().getTimestamp() > movement.getTimestamp()) {
            // uh oh stinky
            return;
        }

        // Move the opposite player
        Board board = Board.getInstance();
        board.getOtherPlayer().setPosition(movement.getNewX(), movement.getNewY());


//        FirstObjectToSend msg = new FirstObjectToSend((LinkedHashMap<String, Object>) msgFromJSon.getObject());
//        System.out.println("Received first message " + msg);
//
//        PlayRunner.messageAccumulator.queueMessage(new Message<>(new ReplyObject("Got " + msg
//                + "!!!!")));

    }
}
