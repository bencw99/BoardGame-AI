package game.piece;

import java.util.ArrayList;

import game.Move;
import game.board.Location;
import game.board.Node;
import game.piece.Piece.Loyalty;

/**
 * A class describing a checkers king piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class King extends Piece
{
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 */
	public King(Loyalty loyalty)
	{
		super(loyalty);
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 */
	public King(Loyalty loyalty, Node node)
	{
		super(loyalty, node);
	}

	public ArrayList<Move> getPossibleMoves() 
	{
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		for(int i = - 1; i < 1; i += 2)
		{
			for(int j = -1; j < 1; j += 2)
			{
				Location currentLoc = new Location(getNode().getLoc().getRow() + i, getNode().getLoc().getCol() + j);
				
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
		}
		
		for(ArrayList<Node> move : getNextJumps(getNode().getLoc()))
		{
			possibleMoves.add(new Move(move, getNode().getBoard()));
		}
		
		return possibleMoves;
	}

	protected ArrayList<ArrayList<Node>> getNextJumps(Location loc)
	{
		ArrayList<ArrayList<Node>> retVal = new ArrayList<ArrayList<Node>>();
		
		ArrayList<Location> jumps = new ArrayList<Location>();
		
		for(int i = -1; i <= 1; i += 1)
		{
			for(int j = -1; j <= 1; j += 1)
			{
				Location possibleJumpLoc = new Location(loc.getRow() + 2*i, loc.getCol() + 2*j);
				
				Location interJumpLoc = new Location(loc.getRow() + i, loc.getCol() + j);
				
				if(getNode().getBoard().isValid(possibleJumpLoc) && getNode().getBoard().getPiece(interJumpLoc).getLoyalty() != this.getLoyalty())
				{
					jumps.add(possibleJumpLoc);
				}
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
