package display;

import display.entity.Item;
import display.entity.Player;

import java.util.ArrayList;

public class Board {

    public static final int BOARD_WIDTH = 54;
    public static final int BOARD_HEIGHT = 41;
    char[][] boardArray = new char[BOARD_WIDTH][BOARD_HEIGHT];
    private ArrayList<Player> players = new ArrayList<>();

    /**
     * - and | are walls
     * * are levers
     * D are closed doors
     * P are players
     * 0 are Pressure plates
     * B are boxes
     */
    private String startingBoard =
            "------------------------     ------------------------\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      *     *                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "------|---D|------------     --------|---D|----------\n" +
            "      |    |                         |    |          \n" +
            "      |    |                         |    |          \n" +
            "------|--- |------------     --------|--- |----------\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|     O          O     |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|     B                |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "------|---D|------------     ------|---D|------------\n" +
            "      |    |                       |    |            \n" +
            "      |    |                       |    |            \n" +
            "------|--- |------------     ------|--- |------------\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      *\n" +
            "|                      |     |                      |\n" +
            "|    O         O       |-----|     O           O    |\n" +
            "|                      | O O |                      |\n" +
            "|                      D     D                      |\n" +
            "|         B            |-----|          B           |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "|                      |     |                      |\n" +
            "------------------------     ------|----D|-----------";

    public Board() {
        setText(startingBoard);
    }

    public boolean isSteppable(int x, int y) {
        return x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT && boardArray[x][y] != '-'
                && boardArray[x][y] != '|' && boardArray[x][y] != 'P' && boardArray[x][y] != 'D'
                && boardArray[x][y] != 'B' && boardArray[x][y] != '*';
    }

    public void markVisibleAround(int x, int y) {

    }

    public int calculateBoardStringPosition(int x, int y) {
        return y * BOARD_WIDTH + x;
    }

    public void setText(String newBoard) {
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

    public Player addPlayer(int x, int y) {
        Player p = new Player(x, y);
        players.add(p);
        return p;
    }

    public ArrayList<Player> getPlayers() {
        return players;
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
}
