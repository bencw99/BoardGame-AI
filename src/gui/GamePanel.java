package gui;

import game.Game;
import game.board.CheckersBoard;
import game.board.Location;
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
public class GamePanel extends JPanel implements KeyListener, MouseListener
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
		game = new Game();
		
        frame.setTitle("Checkers Game");
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel();
        panel.setPreferredSize(new Dimension(640, 640));
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
        
        while((!game.getPlayers()[0].isDefeated()) && (!game.getPlayers()[1].isDefeated()))
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
        
//        System.out.println("(" + game.getPlayers()[0].kingWorth + ") (" + game.getPlayers()[1].kingWorth + ")");
//        
//        for(int i = 0; i < 100; i ++)
//        {
//        	game = new Game();
//        	
//	        while((!game.getPlayers()[0].isDefeated()) && (!game.getPlayers()[1].isDefeated()))
//	        {	
//	            try 
//	            {
//	    			game.executeTurn();
//	    		} 
//	            catch (IOException e) 
//	    		{
//	    			e.printStackTrace();
//	    		}
//	        }
//	        
//	        if(game.getPlayers()[0].isDefeated())
//	        {
//	        	redWinCounter ++;
//	        }
//	        else
//	        {
//	        	blackWinCounter ++;
//	        }
//	        
//	        System.out.println(redWinCounter + " " + blackWinCounter);
//        }
        
	}
	
	/**
	 * Draws this game
	 */
	public void paintComponent(Graphics graphics)
	{
		game.draw(graphics);
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent event) 
	{
		int x = event.getX();
		int y = event.getY();
		
		int row = (x - x % CheckersBoard.NODE_WIDTH)/CheckersBoard.NODE_WIDTH;
		int col = (y - y % CheckersBoard.NODE_HEIGHT)/CheckersBoard.NODE_HEIGHT;
		
		Location loc = new Location(row, col);
        
        Player player = game.getPlayers()[game.getTurn().getVal()];
        
        if(player instanceof Human)
        {
        	
        }
        	
        frame.repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
