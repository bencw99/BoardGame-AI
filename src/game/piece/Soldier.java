package game.piece;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import game.Move;
import game.board.Board;
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
		
		int orientation = getLoyalty() == Loyalty.RED ? 1 : -1;
		
		for(int i = - 1; i <= 1; i += 2)
		{
			Location currentLoc = new Location(getNode().getLoc().getRow() + orientation, getNode().getLoc().getCol() + i);
			
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
			if(move.size() > 1)
			{
				possibleMoves.add(new Move(move, getNode().getBoard()));
			}
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
		
		int orientation = getLoyalty() == Loyalty.RED ? 1 : -1;
		
		for(int i = -1 ; i <= 1; i += 2)
		{
			Location possibleJumpLoc = new Location(loc.getRow() + 2*orientation, loc.getCol() + 2*i);
			
			Location interJumpLoc = new Location(loc.getRow() + orientation, loc.getCol() + i);
			
			if(getNode().getBoard().isValid(possibleJumpLoc) && getNode().getBoard().getPiece(possibleJumpLoc) == null && getNode().getBoard().getPiece(interJumpLoc) != null && getNode().getBoard().getPiece(interJumpLoc).getLoyalty() != this.getLoyalty())
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

	public void draw(Graphics graphics) 
	{
		graphics.setColor(getLoyalty() == Loyalty.RED ? Color.RED : Color.DARK_GRAY);
		graphics.fillOval(getNode().getLoc().getRow()*Board.NODE_WIDTH, getNode().getLoc().getCol()*Board.NODE_HEIGHT, Board.NODE_WIDTH, Board.NODE_HEIGHT);
	}
}
