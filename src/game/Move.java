package game;

import java.util.ArrayList;

/**
 * A class representing a move of a piece in a checkers game 
 * 
 * @author Benjamin Cohen-Wang
 */
public class Move
{
	/** The node this move starts at **/
	private Node start;
	
	/** The node this move ends at **/
	private Node end;
	
	/** The array list of nodes between the starting and ending nodes **/
	private ArrayList<Node> interNodes;
	
	/** The array list of pieces jumped in this move **/
	private ArrayList<Piece> jumped;
	
	/** The board this move is executed on **/
	private Board board;
	
	/**
	 * Parameterized Constructor, initializes start and end nodes, board, and interNodes
	 * 
	 * @param start	the node this move starts at
	 * @param end	the node this move ends at
	 * @param interNodes	the array list of nodes jumped through
	 * @param board	the board this move occurs in
	 */
	public Move(Node start, Node end, ArrayList<Node> interNodes, Board board)
	{
		this.start = start;
		this.end = end;
		this.interNodes = interNodes;
		this.board = board;
		
		jumpedInit();
	}
	
	/**
	 * Adds jumped pieces to the jump array list
	 */
	public void jumpedInit()
	{
		Node current = start;
		Node next;
		
		for(int i = 0; i <= interNodes.size(); i ++)
		{
			if(i == interNodes.size())
			{
				next = end;
			}
			else
			{
				next = interNodes.get(i);
			}
			
			if(Math.abs(next.getLoc().getRow() - current.getLoc().getRow()) > 1)
			{
				jumped.add(board.get((next.getLoc().getRow() + current.getLoc().getRow())/2 , (next.getLoc().getCol() + current.getLoc().getCol())/2));
			}
			
			current = next;
		}
	}
	
	/**
	 * Returns whether or not this move is simple
	 * 
	 * @return a boolean describing whether or not this move is simple
	 */
	public boolean isSimple()
	{
		if(interNodes.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns the array list of pieces jumped in this move
	 * 
	 * @return the array list of pieces jumped in this move
	 */ 
	public ArrayList<Piece> getJumped()
	{
		return jumped;
	}
	
	/**
	 * Returns the array list of nodes moved through in this move
	 * 
	 * @return the array list of nodes moved through
	 */ 
	public ArrayList<Node> getInterNodes()
	{
		return interNodes;
	}
	
	/**
	 * Returns the node this move starts at
	 * 
	 * @return the node this move starts at
	 */
	public Node getStart()
	{
		return start;
	}
	
	/**
	 * Returns the node this move ends at
	 * 
	 * @return the node this move ends at
	 */
	public Node getEnd()
	{
		return end;
	}
}
