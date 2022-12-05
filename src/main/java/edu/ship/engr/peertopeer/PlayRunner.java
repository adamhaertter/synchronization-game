package edu.ship.engr.peertopeer;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.ship.engr.display.GUI;
import edu.ship.engr.communication.ConnectionManager;
import edu.ship.engr.communication.MessageAccumulator;
import edu.ship.engr.messages.FirstObjectToSend;
import edu.ship.engr.messages.Message;

import javax.swing.*;

public class PlayRunner
{
    public static MessageAccumulator messageAccumulator  =
        new MessageAccumulator();

    public static boolean IS_HOST = true;

    public static void main(String[] args)
            throws IOException, ClassNotFoundException
    {

        System.out.println("RUNNING");

        IS_HOST = isHost(args);

        GUI gui = new GUI();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setPreferredSize(new Dimension(400, 800));
        gui.pack();
        if(IS_HOST)
            gui.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4, 50);
        else
            gui.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()*2/3, 50);
        gui.setVisible(true);

        Socket socket;
        if (IS_HOST)
        {
            try (ServerSocket serverSocket = new ServerSocket(4242, 10))
            {
                socket = serverSocket.accept();
            }
        }
        else
        {
            socket = new Socket("localhost", 4242);
        }

        new ConnectionManager(socket, messageAccumulator);
        if (IS_HOST)
        {
            // add player to board. else condition if not host is for adding second player to board and sending the join message to the host
            // after host receives, sends a response back with his own join message
            System.out.println("Sending msg");
            FirstObjectToSend x = new FirstObjectToSend(42, 59);
            messageAccumulator.queueMessage(new Message<>(x));
        }
    }

    private static boolean isHost(String[] args)
    {
        return args.length >= 1 && args[0].equals("host");
    }


}

