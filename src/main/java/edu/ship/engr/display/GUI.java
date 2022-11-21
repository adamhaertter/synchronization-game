package edu.ship.engr.display;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.entity.Item;
import edu.ship.engr.display.entity.Player;
import edu.ship.engr.enums.Direction;
import edu.ship.engr.messages.Message;
import edu.ship.engr.messages.MovementMessage;
import edu.ship.engr.peertopeer.PlayRunner;

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

	private static final String TITLE_BAR_HOST = "Puzzle Game (Host)";
	private static final String TITLE_BAR_PEER = "Puzzle Game (Peer)";
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Timer timer;
	private JTextComponent boardBox;
	private Board board;
	private Player playerOne;
	private Player playerTwo;
	private Player myPlayer;

	/**
	 * Create it and show it!
	 */
	public GUI()
	{
		// set the title and dimensions of your window
		if(PlayRunner.IS_HOST)
			setTitle(TITLE_BAR_HOST);
		else
			setTitle(TITLE_BAR_PEER);
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		boardBox = new JTextArea();
		boardBox.setText("Starting");
		boardBox.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		this.add(boardBox);

		boardBox.addKeyListener(new KeyProcessor());

		startTheTimer();
		board = Board.getInstance();
		this.playerOne = board.getPlayerOne();
		this.playerTwo = board.getPlayerTwo();

		if (PlayRunner.IS_HOST) {
			myPlayer = playerOne;
		} else {
			myPlayer = playerTwo;
		}
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

		calcMoveForPlayer(this.myPlayer);

		// here you should create the string that is the entire display (with \n
		// between lines)
		// use board.setText to send that string to the display
		StringBuffer boardString = new StringBuffer(board.toString());
		putPlayersOnTheScreen(boardString);
		boardBox.setText(boardString.toString());
		invalidate();
		repaint();
	}

	private void calcMoveForPlayer(Player player) {
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
	}

	private void putPlayersOnTheScreen(StringBuffer boardString)
	{
		// Player one
		int positionInString = board.calculateBoardStringPosition(playerOne.getX(), playerOne.getY());
		boardString.setCharAt(positionInString, playerOne.getDisplayChar());

		// Player two
		positionInString = board.calculateBoardStringPosition(playerTwo.getX(), playerTwo.getY());
		boardString.setCharAt(positionInString, playerTwo.getDisplayChar());
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
				myPlayer.calculateUpcomingMove(Direction.Left);
			}
			if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
			{
				myPlayer.calculateUpcomingMove(Direction.Right);
			}
			if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
			{
				myPlayer.calculateUpcomingMove(Direction.Up);
			}
			if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
			{
				myPlayer.calculateUpcomingMove(Direction.Down);
			}
			if (keyCode == KeyEvent.VK_SPACE) {
				// check player direction. check tile where facing
				// grab tile from board
			}
		}

	}
}
