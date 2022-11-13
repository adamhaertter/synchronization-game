package edu.ship.engr.peertopeer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.ship.engr.communication.ConnectionManager;
import edu.ship.engr.communication.MessageAccumulator;
import edu.ship.engr.messages.FirstObjectToSend;
import edu.ship.engr.messages.Message;

public class PlayRunner
{
    public static MessageAccumulator messageAccumulator  =
        new MessageAccumulator();;

    public static void main(String[] args)
            throws IOException, ClassNotFoundException
    {

        System.out.println("RUNNING");
        Socket socket;
        if (isHost(args))
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
        if (isHost(args))
        {
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

