package game;

import game.board.Board;
import game.board.Location;
import game.board.Node;
import game.piece.Piece;

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
	private ArrayList<Piece> jumped;
	
	/** The board this move is executed on **/
	private Board board;
	
	/**
	 * Parameterized Constructor, initializes start and end nodes, board, and interNodes
	 * 
	 * @param interNodes	the array list of nodes jumped through
	 * @param board	the board this move occurs in
	 */
	public Move(ArrayList<Node> nodes, Board board)
	{
		this.nodes = nodes;
		this.board = board;
		
		loadJumped();
	}
	
	/**
	 * Adds jumped pieces to the jump array list
	 */
	public void loadJumped()
	{
		Node current = nodes.get(0);
		Node next;
		
		for(int i = 0; i <= nodes.size(); i ++)
		{
			next = nodes.get(i);
			
			if(Math.abs(next.getLoc().getRow() - current.getLoc().getRow()) > 1)
			{
				jumped.add(board.getPiece(new Location((next.getLoc().getRow() + current.getLoc().getRow())/2, (next.getLoc().getCol() + current.getLoc().getCol())/2)));
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
	public ArrayList<Node> getNodes()
	{
		return nodes;
	}
}
