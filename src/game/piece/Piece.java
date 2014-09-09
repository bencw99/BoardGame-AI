package game.piece;

import game.Node;

import java.util.ArrayList;

/**
 * A class representing a checkers piece
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class Piece 
{
	
	/** The enum describing the loyalty of this piece **/
	public static enum Loyalty {BLACK, RED};
	
	/** The node that this piece belongs to **/
	private Node node;
	
	/** The loyalty of this piece **/
	private Loyalty loyalty;
	
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 */
	public Piece(Loyalty loyalty)
	{
		this.loyalty = loyalty;
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 */
	public Piece(Loyalty loyalty, Node node)
	{
		this(loyalty);
		this.node = node;
	}
	
	/**
	 * Returns the possible nodes this piece can go to
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public ArrayList<Node> getPossibleMoves()
	{
		for(int i = -1; i <= 1; i += 2)
		{
			
		}
		
		return null;
	}
	
	/**
	 * Returns the possible nodes this piece can jump to
	 * 
	 * @return	the array list of possible moves this piece can jump to
	 */
	public ArrayList<Node> getJumpMoves()
	{
		return null;
	}
	
	/**
	 * Adds this instance to the given node
	 * 
	 * @param node	the node this instance is added to
	 */
	public void add(Node node)
	{
		this.node = node;
	}
}
