package game.piece.chessPieces;

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
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 * @throws IOException 
	 */
	public Rook(Loyalty loyalty) throws IOException
	{
		super(loyalty);
		
		if(!imagesInitialized)
		{
			imagesInit();
		}
		
		image = (getLoyalty() == Loyalty.RED) ? WHITE_ROOK: BLACK_ROOK;
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
		
		if(!imagesInitialized)
		{
			imagesInit();
		}
		
		image = (getLoyalty() == Loyalty.RED) ? WHITE_ROOK: BLACK_ROOK;
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
		super(piece.getLoyalty(), node);
		
		if(!imagesInitialized)
		{
			imagesInit();
		}
		
		image = (getLoyalty() == Loyalty.RED) ? WHITE_ROOK: BLACK_ROOK;
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
			Location currentLoc = new Location(getNode().getLoc().getRow() + count, getNode().getLoc().getCol());
			
			if(!getNode().getBoard().isValid(currentLoc) || getNode().getBoard().getNode(currentLoc).getPiece() != null)
			{
				openSpace = false;
				
				if(getNode().getBoard().isValid(currentLoc) && getNode().getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getNode().getBoard().getNode(currentLoc));
					
					possibleMoves.add(new ChessMove(move, getNode().getBoard(), getLoyalty()));
				}
			}
			else
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getNode().getBoard().getNode(currentLoc));
				
				possibleMoves.add(new ChessMove(move, getNode().getBoard(), getLoyalty()));
			}
			
			count ++;
		}
		
		openSpace = true;
		count = 1;
		
		while(openSpace)
		{
			Location currentLoc = new Location(getNode().getLoc().getRow() - count, getNode().getLoc().getCol());
			
			if(!getNode().getBoard().isValid(currentLoc) || getNode().getBoard().getNode(currentLoc).getPiece() != null)
			{
				openSpace = false;
				
				if(getNode().getBoard().isValid(currentLoc) && getNode().getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getNode().getBoard().getNode(currentLoc));
					
					possibleMoves.add(new ChessMove(move, getNode().getBoard(), getLoyalty()));
				}
			}
			else
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getNode().getBoard().getNode(currentLoc));
				
				possibleMoves.add(new ChessMove(move, getNode().getBoard(), getLoyalty()));
			}
			
			count ++;
		}
		
		openSpace = true;
		count = 1;
		
		while(openSpace)
		{
			Location currentLoc = new Location(getNode().getLoc().getRow(), getNode().getLoc().getCol() + count);
			
			if(!getNode().getBoard().isValid(currentLoc) || getNode().getBoard().getNode(currentLoc).getPiece() != null)
			{
				openSpace = false;
				
				if(getNode().getBoard().isValid(currentLoc) && getNode().getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getNode().getBoard().getNode(currentLoc));
					
					possibleMoves.add(new ChessMove(move, getNode().getBoard(), getLoyalty()));
				}
			}
			else
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getNode().getBoard().getNode(currentLoc));
				
				possibleMoves.add(new ChessMove(move, getNode().getBoard(), getLoyalty()));
			}
			
			count ++;
		}
		
		openSpace = true;
		count = 1;
		
		while(openSpace)
		{
			Location currentLoc = new Location(getNode().getLoc().getRow(), getNode().getLoc().getCol() - count);
			
			if(!getNode().getBoard().isValid(currentLoc) || getNode().getBoard().getNode(currentLoc).getPiece() != null)
			{
				openSpace = false;
				
				if(getNode().getBoard().isValid(currentLoc) && getNode().getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getNode().getBoard().getNode(currentLoc));
					
					possibleMoves.add(new ChessMove(move, getNode().getBoard(), getLoyalty()));
				}
			}
			else
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getNode().getBoard().getNode(currentLoc));
				
				possibleMoves.add(new ChessMove(move, getNode().getBoard(), getLoyalty()));
			}
			
			count ++;
		}
		
		return possibleMoves;
	}
}