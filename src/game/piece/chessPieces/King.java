package game.piece.chessPieces;

import java.io.IOException;
import java.util.ArrayList;

import game.board.node.Location;
import game.board.node.Node;
import game.move.ChessMove;
import game.move.Move;
import game.piece.Piece;

/**
 * A class representing a chess king
 * 
 * @author Benjamin Cohen-Wang
 */
public class King extends Piece
{
	/** The worth of a king **/
	public static final int KING_WORTH = Integer.MAX_VALUE;
	
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 * @throws IOException 
	 */
	public King(Loyalty loyalty) throws IOException
	{
		super(loyalty);
		setWorth(KING_WORTH);
		image = (getLoyalty() == Loyalty.RED) ? WHITE_KING: BLACK_KING;
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
		image = (getLoyalty() == Loyalty.RED) ? WHITE_KING: BLACK_KING;
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 * @throws IOException 
	 */
	public King(Piece piece, Node node) throws IOException
	{
		super(piece, node);
		setWorth(KING_WORTH);
		image = (getLoyalty() == Loyalty.RED) ? WHITE_KING: BLACK_KING;
	}

	/**
	 * Returns the possible move this piece can do
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		for(int i = -1; i <= 1; i ++)
		{
			for (int j = -1; j <= 1; j ++)
			{
				Location moveLoc = new Location(getLoc().getRow() + i, getLoc().getCol() + j);
				
				if(!moveLoc.equals(getLoc()))
				{
					if(getBoard().isValid(moveLoc) && (getBoard().getNode(moveLoc).getPiece() == null || getBoard().getNode(moveLoc).getPiece().getLoyalty() != this.getLoyalty()))
					{
						ArrayList<Node> move = new ArrayList<Node>();
						
						move.add(getNode());
						move.add(getBoard().getNode(moveLoc));
						
						possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
					}
				}
			}
		}
		
		if(!hasMoved())
		{
			boolean openSpace = true;
			int count = 1;
			
			while(openSpace)
			{
				Location currentLoc = new Location(getLoc().getRow(), getLoc().getCol() + count);
				
				if(!getBoard().isValid(currentLoc) || getBoard().getNode(currentLoc).getPiece() != null)
				{
					openSpace = false;
					
					if(getBoard().isValid(currentLoc) && getBoard().getNode(currentLoc).getPiece() instanceof Rook)
					{
						if(!getBoard().getNode(currentLoc).getPiece().hasMoved())
						{
							ArrayList<Node> move = new ArrayList<Node>();
							
							move.add(getNode());
							move.add(getBoard().getNode(new Location(getNode().getLoc().getRow(), getNode().getLoc().getCol() + 2)));
							
							possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
						}
					}
				}
				
				count ++;
			}
			
			openSpace = true;
			count = 1;
			
			while(openSpace)
			{
				Location currentLoc = new Location(getLoc().getRow(), getLoc().getCol() - count);
				
				if(!getBoard().isValid(currentLoc) || getBoard().getNode(currentLoc).getPiece() != null)
				{
					openSpace = false;
					
					if(getBoard().isValid(currentLoc) && getBoard().getNode(currentLoc).getPiece() instanceof Rook)
					{
						if(!getBoard().getNode(currentLoc).getPiece().hasMoved())
						{
							ArrayList<Node> move = new ArrayList<Node>();
							
							move.add(getNode());
							move.add(getBoard().getNode(new Location(getNode().getLoc().getRow(), getNode().getLoc().getCol() - 2)));
							
							possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
						}
					}
				}
				
				count ++;
			}
		}
		
		return possibleMoves;
	}
	
	/**
	 * Returns a clone of this piece at the given node
	 * 
	 * @param node	the node to be cloned to
	 * @return	the cloned piece
	 * @throws IOException 
	 */
	public Piece clone(Node node) throws IOException
	{
		return new King(this, node);
	}
}
