package edu.ship.engr.communication.handlers;

import edu.ship.engr.messages.Message;

import java.util.LinkedHashMap;

public class ReplyObjectHandler implements Handler
{
    @Override
    public void processMessage(Message<?> msg)
    {
        LinkedHashMap<String, Object> p =
                (LinkedHashMap<String, Object>) msg.getObject();
        String text = (String) p.get("text");

        System.out.println("Received dimension reply " + text);

    }

}
