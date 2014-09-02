package game;

import java.awt.Color;

/**
 * A class representing a checkers board node
 * 
 * @author Benjamin Cohen-Wang
 */
public class Node 
{
	/** The board containing this node **/
	private Board board;
	
	/** The piece currently contained in this node **/
	private Piece piece;
	
	/** The color of this node **/
	private Color color;
	
	/**
	 * Default constructor
	 */
	public Node()
	{
		piece = null;
	}
	
	/**
	 * Parameterized constructor, initializes color to given color
	 */
	public Node(Color color)
	{
		piece = null;
		this.color = color;
	}
}
