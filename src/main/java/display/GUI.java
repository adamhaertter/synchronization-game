package display;

import display.entity.Item;
import display.entity.Player;
import enums.Direction;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.text.JTextComponent;

/**
 * The GUI for text based games which also serves as the game engine (controls
 * the clock and handles keyboard input)
 *
 * @author Merlin
 *
 */
public class GUI extends JFrame implements ActionListener
{

	private static final String TITLE_BAR_START = "Rogue!!!! ";
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Timer timer;
	private JTextComponent boardBox;
	private Board board;
	private Player player;

	/**
	 * Create it and show it!
	 */
	public GUI()
	{
		// set the title and dimensions of your window
		setTitle(TITLE_BAR_START);
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		boardBox = new JTextArea();
		boardBox.setText("Starting");
		boardBox.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		this.add(boardBox);

		boardBox.addKeyListener(new KeyProcessor());

		startTheTimer();
		board = new Board();
		player = board.addPlayer(2, 1);
		//player2 = board.addPlayer();
	}

	private void startTheTimer()
	{
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
	public void actionPerformed(ActionEvent e)
	{
		//player.calculateUpcomingMove();
		if (board.isSteppable(player.getNextX(), player.getNextY()))
		{
			player.move();
			Item x = board.getItem(player.getX(), player.getY());
			if (x != null)
			{
				player.addToInventory(x);
			}
			//TODO get the item off the board
			board.markVisibleAround(player.getX(), player.getY());
		}

		// here you should create the string that is the entire display (with \n
		// between lines)
		// use board.setText to send that string to the display
		StringBuffer boardString = new StringBuffer(board.toString());
		putPlayersOnTheScreen(boardString);
		boardBox.setText(boardString.toString());
		invalidate();
		repaint();
	}

	private void putPlayersOnTheScreen(StringBuffer boardString)
	{
		ArrayList<Player> players = board.getPlayers();
		for (Player player : players) {
			int positionInString = board.calculateBoardStringPosition(player.getX(), player.getY());
			boardString.setCharAt(positionInString, player.getDisplayChar());
		}
	}

	private class KeyProcessor extends KeyAdapter
	{

		/**
		 * This is where events will come when the user presses a key
		 *
		 * @see KeyAdapter#keyPressed(KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
			{
				player.calculateUpcomingMove(Direction.Left);
			}
			if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
			{
				player.calculateUpcomingMove(Direction.Right);
			}
			if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
			{
				player.calculateUpcomingMove(Direction.Up);
			}
			if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
			{
				player.calculateUpcomingMove(Direction.Down);
			}
			if (keyCode == KeyEvent.VK_SPACE) {
				// check player direction. check tile where facing
				// grab tile from board
			}
		}

	}
}
