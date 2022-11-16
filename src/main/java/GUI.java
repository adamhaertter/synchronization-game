import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		board = new Board(7);
		player = new Player(board.getStartingX(), board.getStartingY());
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
		player.calculateUpcomingMove();
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
		putPlayerOnTheScreen(boardString);
		boardBox.setText(boardString.toString());
		invalidate();
		repaint();
	}

	private void putPlayerOnTheScreen(StringBuffer boardString)
	{
		int positionInString = board.calculateBoardStringPosition(player.getX(), player.getY());
		boardString.setCharAt(positionInString, player.getDisplayChar());
	}

	private class KeyProcessor extends KeyAdapter
	{

		/**
		 * This is where events will come when the user presses a key
		 * 
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_LEFT)
			{
				player.setDirection(Player.WEST);
			}
			if (keyCode == KeyEvent.VK_RIGHT)
			{
				player.setDirection(Player.EAST);
			}
			if (keyCode == KeyEvent.VK_UP)
			{
				player.setDirection(Player.NORTH);
			}
			if (keyCode == KeyEvent.VK_DOWN)
			{
				player.setDirection(Player.SOUTH);
			}
		}

	}
}
