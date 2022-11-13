package edu.ship.engr.messages;

import java.util.LinkedHashMap;

/**
 * This is just an example message that carries a number
 */
public class FirstObjectToSend
{
    private final int magicNumber;
    private final int magicNumber2;

    /**
     *
     * @param magicNumber our number
     */
    public FirstObjectToSend(int magicNumber, int magicNumber2)
    {
        this.magicNumber = magicNumber;
        this.magicNumber2 = magicNumber2;
    }

    public int getMagicNumber2()
    {
        return magicNumber2;
    }

    public FirstObjectToSend(LinkedHashMap<String, Object> p)
    {
        magicNumber = (Integer) p.get("magicNumber");
        magicNumber2 = (Integer) p.get("magicNumber2");
    }

    @Override
    public String toString()
    {
        return "FirstMessage{" +
                "magicNumber=" + magicNumber +
                ", magicNumber2=" + magicNumber2 +
                '}';
    }

    public int getMagicNumber()
    {
        return magicNumber;
    }

}
