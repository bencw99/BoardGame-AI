package game.piece;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;

import game.Move;
import game.board.Board;
import game.board.Location;
import game.board.Node;

/**
 * A class describing a checkers king piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class King extends Piece implements ImageObserver
{
	/** The worth of a king **/
	public static final int KING_WORTH = 20;
	
	/**
	 * Default constructor
	 * 
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 * @throws IOException 
	 */
	public King(Loyalty loyalty) throws IOException
	{
		super(loyalty);
		setWorth(KING_WORTH);
		
		if(!imagesInitialized)
		{
			checkerImagesInit();
		}
		
		image = (getLoyalty() == Loyalty.RED) ? RED_KING: BLACK_KING;
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 * @throws IOException 
	 */
	public King(Loyalty loyalty, Node node) throws IOException
	{
		super(loyalty, node);
		setWorth(KING_WORTH);
		
		if(!imagesInitialized)
		{
			checkerImagesInit();
		}
		
		image = (getLoyalty() == Loyalty.RED) ? RED_KING: BLACK_KING;
	}

	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 */
	public King(Piece piece, Node node)
	{
		super(piece.getLoyalty(), node);
		setWorth(KING_WORTH);
	}
	
	/**
	 * Returns the possible nodes this piece can go to
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public ArrayList<Move> getPossibleMoves() 
	{
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		for(int i = - 1; i <= 1; i += 2)
		{
			for(int j = -1; j <= 1; j += 2)
			{
				Location currentLoc = new Location(getNode().getLoc().getRow() + j, getNode().getLoc().getCol() + i);
				
				if(getNode().getBoard().isValid(currentLoc))
				{
					if(getNode().getBoard().getPiece(currentLoc) == null)
					{
						ArrayList<Node> move = new ArrayList<Node>();
						move.add(getNode());
						move.add(getNode().getBoard().getNode(currentLoc));
						
						possibleMoves.add(new Move(move, getNode().getBoard(), getLoyalty()));
					}
				}
			}
		}
		
		for(ArrayList<Node> move : getNextJumps(getNode().getLoc(), new ArrayList<Location>()))
		{
			if(move.size() > 1)
			{
				possibleMoves.add(new Move(move, getNode().getBoard(), getLoyalty()));
			}
		}
		
		ArrayList<Move> jumpMoves = new ArrayList<Move>();
		
		for(Move possibleMove : possibleMoves)
		{
			if(!possibleMove.getJumped().isEmpty())
			{
				jumpMoves.add(possibleMove);
			}
		}
		
		if(!jumpMoves.isEmpty())
		{
			return jumpMoves;
		}
		else
		{
			return possibleMoves;
		}
	}
	
	/**
	 * Returns the possible nodes this piece can jump to
	 * 
	 * @return	the array list of possible nodes this piece can jump to
	 */
	protected ArrayList<ArrayList<Node>> getNextJumps(Location loc, ArrayList<Location> pastJumpLocs)
	{
		ArrayList<ArrayList<Node>> retVal = new ArrayList<ArrayList<Node>>();
		
		ArrayList<Location> jumps = new ArrayList<Location>();
		
		for(int i = -1 ; i <= 1; i += 2)
		{
			for(int j = -1 ; j <= 1; j += 2)
			{
				Location possibleJumpLoc = new Location(loc.getRow() + 2*j, loc.getCol() + 2*i);
				
				Location interJumpLoc = new Location(loc.getRow() + j, loc.getCol() + i);
				
				if(getNode().getBoard().isValid(possibleJumpLoc) && getNode().getBoard().getPiece(possibleJumpLoc) == null && getNode().getBoard().getPiece(interJumpLoc) != null && getNode().getBoard().getPiece(interJumpLoc).getLoyalty() != this.getLoyalty())
				{
					boolean isValid = true;
					
					for(Location pastJumpLoc : pastJumpLocs)
					{
						if((interJumpLoc.getRow() == pastJumpLoc.getRow()) && (interJumpLoc.getCol() == pastJumpLoc.getCol()))
						{
							isValid = false;
						}
					}
						
					if(isValid)
					{
						jumps.add(possibleJumpLoc);
					}
				}
			}
		}

		/*
		 * Bug Summary:
		 * 
		 * Despite the testing that occurs to remove various invalid moves resulting from recursion and rejumping a single space,
		 * An overflow occurs because the method is called recursively before this testing takes place, hence it works for single
		 * jumps, but not for 2+ jumps of a king
		 * 
		 * To fix this error, incorporate testing into recursive calls to prevent new calls when move is not even valid
		 */
		
		for(Location jump : jumps)
		{
			ArrayList<Location> newPastJumpLocs = (ArrayList<Location>) pastJumpLocs.clone();
			
			newPastJumpLocs.add(new Location((loc.getRow() + jump.getRow())/2, (loc.getCol() + jump.getCol())/2));
			
			ArrayList<ArrayList<Node>> movesOfCurrent = getNextJumps(jump, newPastJumpLocs);
			
			for(ArrayList<Node> thisMoveOfCurrent : movesOfCurrent)
			{
				boolean validMove = true;
				
//				for(Node jumped : (new Move(thisMoveOfCurrent, getNode().getBoard(), getLoyalty())).getJumped())
//				{
//					if(jumped.getLoc().getRow() == (loc.getRow() + jump.getRow())/2 && jumped.getLoc().getCol() == (loc.getCol() + jump.getCol())/2)
//					{
//						validMove = false;
//					}
//				}
				
				if(validMove)
				{
					thisMoveOfCurrent.add(0, getNode().getBoard().getNode(loc));
					retVal.add(thisMoveOfCurrent);
				}
			}
		}

		if(retVal.isEmpty())
		{
			ArrayList<Node> thisLoc = new ArrayList<Node>();
			thisLoc.add(getNode().getBoard().getNode(loc));
			
			retVal.add(thisLoc);
		}
		
		return retVal;
	}

	public void draw(Graphics graphics) 
	{
		graphics.drawImage(image, getNode().getLoc().getRow()*Board.NODE_WIDTH + 9, getNode().getLoc().getCol()*Board.NODE_HEIGHT + 9, this);
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
	{
		return false;
	}
}
