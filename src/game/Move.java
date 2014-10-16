package game;

import game.board.Board;
import game.board.Location;
import game.board.Node;
import game.piece.Piece.Loyalty;

import java.util.ArrayList;

/**
 * A class representing a move of a piece in a checkers game 
 * 
 * @author Benjamin Cohen-Wang
 */
public class Move
{	
	/** The array list of nodes gone through in this move **/
	private ArrayList<Node> nodes;
	
	/** The array list of pieces jumped in this move **/
	private ArrayList<Node> jumped;
	
	/** The loyalty of this move **/
	private Loyalty loyalty;
	
	/** The board this move is executed on **/
	private Board board;
	
	/**
	 * Parameterized Constructor, initializes start and end nodes, board, interNodes, and loyalty
	 * 
	 * @param interNodes	the array list of nodes jumped through
	 * @param board	the board this move occurs in
	 * @param loyalty	the loyalty of this move
	 */
	public Move(ArrayList<Node> nodes, Board board, Loyalty loyalty)
	{
		this.nodes = nodes;
		this.board = board;
		this.loyalty = loyalty;
		
		loadJumped();
	}
	
	/**
	 * Adds jumped pieces to the jump array list
	 */
	public void loadJumped()
	{
		Node current;
		Node next;
		
		jumped = new ArrayList<Node>();
		
		for(int i = 0; i < nodes.size() - 1; i ++)
		{
			current = nodes.get(i);
			next = nodes.get(i + 1);
			
			if(Math.abs(next.getLoc().getRow() - current.getLoc().getRow()) == 2)
			{
				jumped.add(board.getNode(new Location((next.getLoc().getRow() + current.getLoc().getRow())/2, (next.getLoc().getCol() + current.getLoc().getCol())/2)));
			}
		}
	}
	
	/**
	 * Returns whether or not this move is simple
	 * 
	 * @return a boolean describing whether or not this move is simple
	 */
	public boolean isSimple()
	{
		if(nodes.size() == 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns a String representation of this move 
	 */
	public String toString()
	{
		String string = "";
		
		for(Node node : nodes)
		{
			string += "(" + node.getLoc().getRow() + ", " + node.getLoc().getCol() + "), ";
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
	public Board getBoard() 
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
}
