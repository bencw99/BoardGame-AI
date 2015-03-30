package gui;

import game.Game;
import game.Game.GameType;
import game.board.CheckersBoard;
import game.board.node.Location;
import game.player.Human;
import game.player.Player;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Panel in which the game is drawn
 * 
 * @author Benjamin Cohen-Wang
 */
public class GamePanel extends JPanel
{
	/** The game of this game panel **/
	private static Game game;
	
    public static JFrame frame = new JFrame();
    
    public static boolean gameOver = false;
    
    public static int redWinCounter = 0;
    
    public static int blackWinCounter = 0;
	
    public GamePanel()
    {
    	super();
    }
    
	public static void main(String[] args) throws IOException
	{
		game = new Game(GameType.CHESS);
		
        frame.setTitle("Board Game");
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel();
        panel.setPreferredSize(new Dimension(game.getBoard().getNodeWidth()*8, game.getBoard().getNodeHeight()*8));
        frame.add(panel);
        
        for(Player player : game.getPlayers())
        {
        	if(player instanceof Human)
        	{
		        panel.addMouseListener((MouseListener) player);
		        panel.addMouseMotionListener((MouseMotionListener) player);
		        frame.addKeyListener((KeyListener) player);
        	}
        }
        
        frame.setVisible(true);
        frame.setLocation(new Point(500, 100));
        frame.pack();
        
        while(!game.isCompleted())
        {	
            try 
            {
    			game.executeTurn();
    		} 
            catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
            	
            frame.repaint();
        }
	}
	
	/**
	 * Draws this game
	 */
	public void paintComponent(Graphics graphics)
	{
		game.draw(graphics);
	}
}
