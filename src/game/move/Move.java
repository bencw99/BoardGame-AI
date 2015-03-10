package game.move;

import game.board.RectangularBoard;
import game.board.node.Node;
import game.piece.Piece.Loyalty;

import java.util.ArrayList;

/**
 * A class representing a move of a board game
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class Move
{
	/** The array list of nodes gone through in this move **/
	private ArrayList<Node> nodes;
	
	/** The array list of pieces jumped in this move **/
	private ArrayList<Node> jumped;
	
	/** The loyalty of this move **/
	private Loyalty loyalty;
	
	/** The board this move is executed on **/
	private RectangularBoard board;
	
	/**
	 * Parameterized Constructor, initializes start and end nodes, board, interNodes, and loyalty
	 * 
	 * @param interNodes	the array list of nodes jumped through
	 * @param board	the board this move occurs in
	 * @param loyalty	the loyalty of this move
	 */
	public Move(ArrayList<Node> nodes, RectangularBoard board, Loyalty loyalty)
	{
		this.nodes = nodes;
		this.board = board;
		this.loyalty = loyalty;
		
		loadJumped();
	}

	/**
	 * Adds jumped pieces to the jump array list
	 */
	public abstract void loadJumped();
	
	/**
	 * Returns a String representation of this move 
	 */
	public String toString()
	{
		String string = "";
		
		for(Node node : nodes)
		{
			string += node.getLoc().toString();
		}
		
		return string;
	}
	
	/**
	 * Returns the array list of nodes jumped in this move
	 * 
	 * @return the array list of nodes jumped in this move
	 */ 
	public ArrayList<Node> getJumped()
	{
		return jumped;
	}
	
	/**
	 * Returns the array list of nodes moved through in this move
	 * 
	 * @return the array list of nodes moved through
	 */ 
	public ArrayList<Node> getNodes()
	{
		return nodes;
	}

	/**
	 * @return the board
	 */
	public RectangularBoard getBoard() 
	{
		return board;
	}
	
	/**
	 * @return the loyalty
	 */
	public Loyalty getLoyalty() 
	{
		return loyalty;
	}
	
	/**
	 * Sets the jumped nodes to the given array list
	 * 
	 * @param jumped
	 */
	public void setJumped(ArrayList<Node> jumped)
	{
		this.jumped = jumped;
	}
}
