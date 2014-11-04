package game.board;

import java.awt.Graphics;
import java.util.ArrayList;

import game.Game;

/**
 * A superclass representing a general game board
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class Board
{
	/** The game this board belong to **/
	private Game game;
	
	/** The arraylist of nodes composing this grid **/
	private ArrayList<Node> nodes;
	 
	/**
	 * Parameterized constructor, initializes game to given game
	 */
	public Board(Game game)
	{
		this.game = game;
	}
	
	/** 
	 * Draws this board
	 * 
	 * @param graphics
	 */
	public void draw(Graphics graphics)
	{
		for(Node node : nodes)
		{
			node.draw(graphics);
		}
	}
}
