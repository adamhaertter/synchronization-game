package edu.ship.engr.display;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.entity.*;
import edu.ship.engr.display.entity.Box;
import edu.ship.engr.enums.Direction;
import edu.ship.engr.exceptions.InvalidTargetException;
import edu.ship.engr.messages.InteractMessage;
import edu.ship.engr.messages.Message;
import edu.ship.engr.messages.MovementMessage;
import edu.ship.engr.peertopeer.PlayRunner;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 * The GUI for text based games which also serves as the game engine (controls
 * the clock and handles keyboard input)
 *
 * @author Merlin
 */
public class GUI extends JFrame implements ActionListener {

    private static final String TITLE_BAR_HOST = "Puzzle Game (Host)";
    private static final String TITLE_BAR_PEER = "Puzzle Game (Peer)";

    // Array list of acceptable key inputs
    private static final ArrayList<Integer> acceptableKeys = new ArrayList<>(Arrays.asList(KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT));

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String key = "- and | are walls\n" +
            "* are unactivated levers\n" +
            "# are activated levers\n" +
            "D are closed doors\n" +
            "P are players\n" +
            "0 are pressure plates\n" +
            "B are boxes";

    private Timer timer;
    private JTextComponent boardBox;
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private Player myPlayer;

    /**
     * Create it and show it!
     */
    public GUI() {
        // set the title and dimensions of your window
        if (PlayRunner.IS_HOST)
            setTitle(TITLE_BAR_HOST);
        else
            setTitle(TITLE_BAR_PEER);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        boardBox = new JTextArea();
        boardBox.setText("Starting");
        boardBox.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        boardBox.setEditable(false);
        this.add(boardBox);

        boardBox.addKeyListener(new KeyProcessor());

        startTheTimer();
        board = Board.getInstance();
        this.playerOne = board.getPlayerOne();
        this.playerTwo = board.getPlayerTwo();

        // First logic
        StringBuffer boardString = new StringBuffer(board.toString());
        putPlayersOnTheScreen(boardString);
        boardBox.setText(boardString.toString());

        if (PlayRunner.IS_HOST) {
            myPlayer = playerOne;
        } else {
            myPlayer = playerTwo;
        }
    }

    private void startTheTimer() {
        timer = new Timer(750, this);
        timer.setInitialDelay(750);
        timer.start();

    }

    /**
     * This is where you put things that should happen based on the timer (like
     * updating the GUI)
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Board board = Board.getInstance();
        // refresh the items
        board.refreshItems();
        // here you should create the string that is the entire display (with \n
        // between lines)
        // use board.setText to send that string to the display
        StringBuffer boardString = new StringBuffer(board.toString());
        putPlayersOnTheScreen(boardString);
        boardString.append(updateStats() + '\n');
        boardString.append("\n\n" + key + '\n');
        boardBox.setText(boardString.toString());
        invalidate();
        repaint();
    }

    private void putPlayersOnTheScreen(StringBuffer boardString) {
        // Player one
        int positionInString = board.calculateBoardStringPosition(playerOne.getX(), playerOne.getY());
        boardString.setCharAt(positionInString, playerOne.getDisplayChar());

        // Player two
        positionInString = board.calculateBoardStringPosition(playerTwo.getX(), playerTwo.getY());
        boardString.setCharAt(positionInString, playerTwo.getDisplayChar());
    }

    private String updateStats() {
        String p1Stats = "\nPlayer One: (";
        p1Stats += playerOne.getX() + ", " + playerOne.getY();
        p1Stats += "), " + playerOne.getDirection();
        if (playerOne.getBox() != null)
            p1Stats += ". Currently holding a box.";

        String p2Stats = "\nPlayer Two: (";
        p2Stats += playerTwo.getX() + ", " + playerTwo.getY();
        p2Stats += "), " + playerTwo.getDirection();
        if (playerTwo.getBox() != null)
            p2Stats += ". Currently holding a box.";

        String playerTrack = "\n**You are Player ";
        if (PlayRunner.IS_HOST)
            playerTrack += "One**";
        else
            playerTrack += "Two**";

        //statsBox.setText(p1Stats + "\n" + p2Stats);
        return p1Stats + p2Stats + playerTrack;
    }

    public static void showWinMSG() {
        // add image from img directory
        ImageIcon icon = new ImageIcon("img/moonyShum.png");
        java.awt.Image image = icon.getImage();
        java.awt.Image newimg = image.getScaledInstance(140, 140, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JOptionPane.showMessageDialog(null, "Congratulations! You win!", "Victory!",
                JOptionPane.QUESTION_MESSAGE, icon);
    }

    // function to sout that the player has moved and their new location
    public static void printPlayerMove(Player player) {
        System.out.println("Player " + (PlayRunner.IS_HOST ? "1" : "2") + " has moved to (" + player.getX() + ", " + player.getY() + ")");
    }

    // function to check if an item has the same coordinates as the player's box
    public static boolean itemIsPlayersBox(Player player, Item item) {
        if (player.getBox() != null) {
            return player.getBox().getXPos() == item.getXPos() && player.getBox().getYPos() == item.getYPos();
        }
        return false;
    }

    // function to process an interaction
    public static void processInteraction(Player player, int targetX, int targetY) {
        System.out.println("interact button pressed at " + targetX + ", " + targetY);
        Board board = Board.getInstance();

        Item item = board.getItemAt(targetX, targetY);

        // can't interact with a door
        if (item instanceof Door) return;

        // can place boxes on non items (the floor) and pressure plates
        if (item == null || item instanceof PressurePlate) {
            Item possibleBox = board.getItemAtIfBox(targetX, targetY);
            if (possibleBox == null) {
                System.out.println("Yeet");
                try {
                    player.placeBox();
                } catch (InvalidTargetException ex) {
                    // move on
                }
            } else {
                item = possibleBox;
            }
        }

        if (item instanceof Box) {
            // Check if the current item we are looking at is the player's invisible box
            if (itemIsPlayersBox(player, item)) {
                try {
                    System.out.println("Item is player's box");
                    player.placeBox();
                } catch (InvalidTargetException ex) {
                    // move on
                }
            } else {
                item.interact(player);
            }
        }

        if (item instanceof Lever) {
            item.interact(player);
        }

        board.refreshItems();
    }

    private class KeyProcessor extends KeyAdapter {

        /**
         * This is where events will come when the user presses a key
         *
         * @see KeyAdapter#keyPressed(KeyEvent)
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            // check if the key code is a movement direction
            if (acceptableKeys.contains(keyCode) ) {
                int oldX = playerOne.getX();
                int oldY = playerOne.getY();
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                    if (board.isSteppable(myPlayer.getX() - 1, myPlayer.getY())) {
                        myPlayer.move(myPlayer.getX() - 1, myPlayer.getY(), Direction.Left);
                        printPlayerMove(myPlayer);
                    }
                }
                if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                    if (board.isSteppable(myPlayer.getX() + 1, myPlayer.getY())) {
                        myPlayer.move(myPlayer.getX() + 1, myPlayer.getY(), Direction.Right);
                        printPlayerMove(myPlayer);
                    }
                }
                if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                    if (board.isSteppable(myPlayer.getX(), myPlayer.getY() - 1)) {
                        myPlayer.move(myPlayer.getX(), myPlayer.getY() - 1, Direction.Up);
                        printPlayerMove(myPlayer);
                    }
                }
                if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                    if (board.isSteppable(myPlayer.getX(), myPlayer.getY() + 1)) {
                        myPlayer.move(myPlayer.getX(), myPlayer.getY() + 1, Direction.Down);
                        printPlayerMove(myPlayer);
                    }
                }

                Timestamp ts = Timestamp.getInstance();
                MovementMessage movement = new MovementMessage(myPlayer.getX(), myPlayer.getY(), oldX, oldY,
                        myPlayer.getDirection(), ts.incrementAndGet());
                PlayRunner.messageAccumulator.queueMessage(new Message<>(movement));
            }

            if (keyCode == KeyEvent.VK_SPACE) {
                // check player direction. check tile where facing
                // grab tile from board

                int[] targetPos = myPlayer.getTargetPosition();
                if (targetPos == null) return;
                int targetX = targetPos[0];
                int targetY = targetPos[1];

                processInteraction(myPlayer, targetX, targetY);

                Timestamp ts = Timestamp.getInstance();
                InteractMessage interact = new InteractMessage(targetX, targetY, ts.incrementAndGet());
                PlayRunner.messageAccumulator.queueMessage(new Message<>(interact));
            }
        }

    }
}
