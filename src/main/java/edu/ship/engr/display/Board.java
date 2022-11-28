package edu.ship.engr.display;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.entity.*;
import edu.ship.engr.peertopeer.PlayRunner;

import java.util.ArrayList;

public class Board {
    // Singleton class for keeping track of synchronization through a message counter
    private static Board instance = null;


    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public static final int BOARD_WIDTH = 54;
    public static final int BOARD_HEIGHT = 27;
    char[][] boardArray = new char[BOARD_WIDTH][BOARD_HEIGHT];
    private Player playerOne;
    private Player playerTwo;
    public Player myPlayer;
    public Player otherPlayer;
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * - and | are walls
     * * are levers off
     * # are levers on
     * D are closed doors
     * P are players
     * 0 are pressure plates
     * B are boxes
     */
    private String startingBoard =
            "------------------------     ------------------------\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "------|--- |------------     --------|--- |----------\n" +
            "      |    |                         |    |          \n" +
            "      |    |                         |    |          \n" +
            "------|--- |------------     --------|--- |----------\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |-----|                      |\n" +
            "|                      |     |                      |\n" +
            "|                                                   |\n" +
            "|                      |-----|                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "------------------------     ------|---- |-----------";

    private Board() {
        // Add players
        playerOne = new Player(2, 1);
        playerTwo = new Player(31, 1);

        if (PlayRunner.IS_HOST) {
            myPlayer = playerOne;
            otherPlayer = playerTwo;
        } else {
            myPlayer = playerTwo;
            otherPlayer = playerOne;
        }

        // Add items
        // Level 1
        Lever doorTwoLever = new Lever(24, 6);
        Lever doorOneLever = new Lever(30, 6);
        items.add(doorOneLever);
        items.add(doorTwoLever);
        ArrayList<Item> door1List = new ArrayList<>();
        door1List.add(doorOneLever);
        ArrayList<Item> door2List = new ArrayList<>();
        door2List.add(doorTwoLever);
//        items.add(new Door(door1List, 11, 11));
        items.add(new Door(door2List, 42, 11));

        // Level 2
        items.add(new Box(11, 21));
        items.add(new Box(41, 21));
        items.add(new Box(27, 19));
        PressurePlate pp1 = new PressurePlate(5, 18);
        PressurePlate pp2 = new PressurePlate(15, 18);
        PressurePlate pp3 = new PressurePlate(35, 18);
        PressurePlate pp4 = new PressurePlate(47, 18);
        items.add(pp1);
        items.add(pp2);
        items.add(pp3);
        items.add(pp4);
        ArrayList<Item> door3List = new ArrayList<>();
        door3List.add(pp1);
        door3List.add(pp2);
        Door door3 = new Door(door3List, 24, 20);
        ArrayList<Item> door4List = new ArrayList<>();
        door4List.add(pp3);
        door4List.add(pp4);
        Door door4 = new Door(door4List, 30, 20);
        ArrayList<Item> door5List = new ArrayList<>();
        door5List.add(pp1);
        door5List.add(pp2);
        door5List.add(pp3);
        door5List.add(pp4);
        Door door5 = new Door(door5List, 41, 26);
        items.add(door3);
        items.add(door4);
        items.add(door5);
        refreshBoard(startingBoard);
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }
    public Player getMyPlayer() {
        return myPlayer;
    }

    public boolean isSteppable(int x, int y) {
        return x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT && boardArray[x][y] != '-'
                && boardArray[x][y] != '|' && boardArray[x][y] != 'P' && boardArray[x][y] != 'D'
                && boardArray[x][y] != 'B' && boardArray[x][y] != '*';
    }
    public boolean canPlaceBox(int x, int y) {
        return x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT && boardArray[x][y] != '-'
                && boardArray[x][y] != '|' && boardArray[x][y] != 'P' && boardArray[x][y] != 'D';
    }

    public void markVisibleAround(int x, int y) {

    }

    public int calculateBoardStringPosition(int x, int y) {
        return y * BOARD_WIDTH + x;
    }

    public void refreshBoard(String newBoard) {
        int row = 0;
        int col = 0;
        for (int i = 0; i < BOARD_HEIGHT*BOARD_WIDTH - 1; i++) {
            if (newBoard.charAt(i) == '\n') {
                //go to next row
                row++;
                col = 0;
            }
            boardArray[col][row] = newBoard.charAt(i);
            col++;
        }

        refreshItems();
    }

    /**
     * Refreshes the items on the board
     */
    public void refreshItems() {
        for (Item item : items) {
            if (item instanceof Door) {
                // passing null since this is to force update the state (look into making this cleaner)
                item.interact(null);
                if (item.getState()) {
                    boardArray[item.getXPos()][item.getYPos()] = ' ';
                } else {
                    boardArray[item.getXPos()][item.getYPos()] = 'D';
                }
            } else if (item instanceof Box) {
                if (item.getState()) {
                    boardArray[item.getXPos()][item.getYPos()] = ' ';
                } else {
                    boardArray[item.getXPos()][item.getYPos()] = 'B';
                }
            } else if (item instanceof Lever) {
                if (item.getState()) {
                    boardArray[item.getXPos()][item.getYPos()] = '#';
                } else {
                    boardArray[item.getXPos()][item.getYPos()] = '*';
                }
            } else if (item instanceof PressurePlate) {
                // if the pressure plate is pressed, then it is a 0, otherwise it is nothing since the player or the
                // box will be on it and we don't want to overwrite that
                if (!item.getState()) {
                    boardArray[item.getXPos()][item.getYPos()] = 'O';
                }
            }
        }
    }

    public Item getItem(int x, int y) {
        Item item;
        switch(boardArray[x][y]) {
            case('*'):
                //lever
            case('D'):
                //door
            case('0'):
                // pressure plate
            case('B'):
                // box
            default:
                item = null;
        }
        return item;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();

        for(int i = 0; i < BOARD_HEIGHT; i++) {
            for(int j = 0; j < BOARD_WIDTH; j++) {
                print.append(boardArray[j][i]);
            }
//            print.append('\n');
        }

        return print.toString();
    }

    /**
     * Function to see if any object is at the given coordinates and return it
     * @param x coordinate to check
     * @param y coordinate to check
     * @return the object at the given coordinates, null if none
     */
    public Item getItemAt(int x, int y) {
        for (Item item : items) {
            if (item.getXPos() == x && item.getYPos() == y) {
                return item;
            }
        }
        return null;
    }

    public Item getItemAtIfPressurePlate(int x, int y) {
        for (Item item : items) {
            if (item.getXPos() == x && item.getYPos() == y && item instanceof PressurePlate) {
                return item;
            }
        }
        return null;
    }

    public Item getItemAtIfBox(int x, int y) {
        for (Item item : items) {
            if (item.getXPos() == x && item.getYPos() == y && item instanceof Box) {
                return item;
            }
        }
        return null;
    }
}
