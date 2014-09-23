package gui;

import game.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Panel in which the game is drawn
 * 
 * @author Benjamin Cohen-Wang
 */
public class GamePanel extends JPanel implements KeyListener, MouseMotionListener
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
        frame.addMouseMotionListener(panel);
        frame.setVisible(true);
        frame.pack();
        
        while(true)
        {
        	try
			{
				Thread.sleep(1000);
			} 
        	catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	System.out.println(game.getTurn());
        	
        	game.executeTurn();
        	
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

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
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
}
