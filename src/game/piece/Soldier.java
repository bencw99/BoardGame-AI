package game.piece;

import java.util.ArrayList;

import game.Move;
import game.board.Location;
import game.board.Node;
import game.piece.Piece.Loyalty;

/**
 * A class describing a checkers soldier piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class Soldier extends Piece
{
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 */
	public Soldier(Loyalty loyalty)
	{
		super(loyalty);
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 */
	public Soldier(Loyalty loyalty, Node node)
	{
		super(loyalty, node);
	}
	
	/**
	 * Returns the possible nodes this piece can go to
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		int orientation = getLoyalty() == Loyalty.RED ? -1 : 1;
		
		for(int i = - 1; i < 1; i += 2)
		{
			Location currentLoc = new Location(getNode().getLoc().getRow() + i, getNode().getLoc().getCol() + orientation);
			
			if(getNode().getBoard().isValid(currentLoc))
			{
				if(getNode().getBoard().getPiece(currentLoc) == null)
				{
					ArrayList<Node> move = new ArrayList<Node>();
					move.add(getNode());
					move.add(getNode().getBoard().getNode(currentLoc));
					
					possibleMoves.add(new Move(move, getNode().getBoard()));
				}
			}
		}
		
		for(ArrayList<Node> move : getNextJumps(getNode().getLoc()))
		{
			possibleMoves.add(new Move(move, getNode().getBoard()));
		}
		
		return possibleMoves;
	}
	
	/**
	 * Returns the possible nodes this piece can jump to
	 * 
	 * @return	the array list of possible nodes this piece can jump to
	 */
	protected ArrayList<ArrayList<Node>> getNextJumps(Location loc)
	{	
		ArrayList<ArrayList<Node>> retVal = new ArrayList<ArrayList<Node>>();
		
		ArrayList<Location> jumps = new ArrayList<Location>();
		
		int orientation = getLoyalty() == Loyalty.RED ? -1 : 1;
		
		for(int i = -2 ; i <= 2; i += 4)
		{
			Location possibleJumpLoc = new Location(loc.getRow() + i, loc.getCol() + 2*orientation);
			
			if(getNode().getBoard().isValid(possibleJumpLoc))
			{
				jumps.add(possibleJumpLoc);
			}
		}
		
		if(jumps.isEmpty())
		{
			ArrayList<Node> thisLoc = new ArrayList<Node>();
			thisLoc.add(getNode().getBoard().getNode(loc));
			
			retVal.add(thisLoc);
			
			return retVal;
		}
		else
		{
			for(Location jump : jumps)
			{
				ArrayList<ArrayList<Node>> movesOfCurrent = getNextJumps(jump);
				
				for(ArrayList<Node> thisMoveOfCurrent : movesOfCurrent)
				{
					thisMoveOfCurrent.add(0, getNode().getBoard().getNode(loc));
					retVal.add(thisMoveOfCurrent);
				}
			}
		}
		
		return retVal;

	}
}
