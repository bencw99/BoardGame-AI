package game.player;

import game.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;

import java.util.ArrayList;

/**
 * A class representing a Human player associated with a checkers game
 * 
 * @author Benjamin Cohen-Wang
 */
public class AI extends Player
{
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param pieces	the pieces of this player
	 */
	public AI(String name, Loyalty loyalty, ArrayList<Piece> pieces)
	{
		super(name, loyalty, pieces);
	}

	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 */
	public Move getThisTurnMove()
	{
		Piece selectedPiece;
		
		int randomPiece = (int)(getPieces().size()*Math.random());
		
		selectedPiece = pieces.get(randomPiece);
		
		ArrayList<Move> possibleMoves = selectedPiece.getPossibleMoves();
		
		int randomMove = (int)(possibleMoves.size()*Math.random());
		
		return possibleMoves.get(randomMove);
	}
}
