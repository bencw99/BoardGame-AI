package game.piece.chessPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import game.board.node.Location;
import game.board.node.Node;
import game.move.ChessMove;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;

/**
 * A class representing a chess rook
 * 
 * @author Benjamin Cohen-Wang
 */
public class Rook extends Piece
{
	/** The worth of a rook **/
	public static final int ROOK_WORTH = 5;
	
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 * @throws IOException 
	 */
	public Rook(Loyalty loyalty) throws IOException
	{
		super(loyalty);
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 * @throws IOException 
	 */
	public Rook(Loyalty loyalty, Node node) throws IOException
	{
		super(loyalty, node);
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 * @throws IOException 
	 */
	public Rook(Piece piece, Node node) throws IOException
	{
		super(piece, node);
	}

	/**
	 * Returns the possible move this piece can do
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		boolean openSpace = true;
		int count = 1;
		
		while(openSpace)
		{
			Location currentLoc = new Location(getLoc().getRow() + count, getLoc().getCol());
			
			if(!getBoard().isValid(currentLoc) || getBoard().getNode(currentLoc).getPiece() != null)
			{
				openSpace = false;
				
				if(getBoard().isValid(currentLoc) && getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getBoard().getNode(currentLoc));
					
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
				}
			}
			else
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getBoard().getNode(currentLoc));
				
				possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
			}
			
			count ++;
		}
		
		openSpace = true;
		count = 1;
		
		while(openSpace)
		{
			Location currentLoc = new Location(getLoc().getRow() - count, getLoc().getCol());
			
			if(!getBoard().isValid(currentLoc) || getBoard().getNode(currentLoc).getPiece() != null)
			{
				openSpace = false;
				
				if(getBoard().isValid(currentLoc) && getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getBoard().getNode(currentLoc));
					
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
				}
			}
			else
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getBoard().getNode(currentLoc));
				
				possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
			}
			
			count ++;
		}
		
		openSpace = true;
		count = 1;
		
		while(openSpace)
		{
			Location currentLoc = new Location(getLoc().getRow(), getLoc().getCol() + count);
			
			if(!getBoard().isValid(currentLoc) || getBoard().getNode(currentLoc).getPiece() != null)
			{
				openSpace = false;
				
				if(getBoard().isValid(currentLoc) && getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getBoard().getNode(currentLoc));
					
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
				}
			}
			else
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getBoard().getNode(currentLoc));
				
				possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
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
				
				if(getBoard().isValid(currentLoc) && getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getBoard().getNode(currentLoc));
					
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
				}
			}
			else
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getBoard().getNode(currentLoc));
				
				possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
			}
			
			count ++;
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
		return new Rook(this, node);
	}
	
	/**
	 * @return the worth
	 */
	@Override
	public double getWorth()
	{
		return ROOK_WORTH;
	}
	
	/**
	 * @return the image
	 */
	@Override
	public BufferedImage getImage()
	{
		return (getLoyalty() == Loyalty.RED) ? WHITE_ROOK: BLACK_ROOK;
	}
	
	/**
	 * @return the enumeration of this Piece subclass
	 */
	@Override
	public int getEnum()
	{
		return 4;
	}
}