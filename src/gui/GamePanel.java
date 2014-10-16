package gui;

import game.Game;

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
	
    private static JFrame frame = new JFrame();
	
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
        frame.addKeyListener(panel);
        frame.addMouseListener(panel);
        frame.setVisible(true);
        frame.setLocation(new Point(500, 100));
        frame.pack();
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
        System.out.println(game.getTurn());
    	
        try 
        {
			game.executeTurn();
		} 
        catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	
        frame.repaint();
		
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
	public void mouseClicked(MouseEvent arg0) 
	{
        System.out.println(game.getTurn());
    	
        try 
        {
			game.executeTurn();
		} 
        catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
