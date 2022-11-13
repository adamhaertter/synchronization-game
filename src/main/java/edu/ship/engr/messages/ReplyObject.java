package edu.ship.engr.messages;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * This is an example message that carries a string
 */
public class ReplyObject implements Serializable
{
    private String text;

    public ReplyObject(String s)
    {
        this.text = s;
    }
    public ReplyObject(LinkedHashMap<String, Object> p)
    {
        this.text = (String) p.get("text");
    }
    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "FirstMessageReply{" +
                "text='" + text + '\'' +
                '}';
    }
}
