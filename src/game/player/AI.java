package game.player;

import game.Move;
import game.board.Board;
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
	/** The depth of the minimax search **/
	private static final int MINIMAX_DEPTH = 5;
	
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
		ArrayList<Move> possibleMoves = getPossibleMoves();
		
		int randomMove = (int)(possibleMoves.size()*Math.random());
		
		System.out.println(possibleMoves.get(randomMove));
		
		System.out.println("Jumped: " + possibleMoves.get(randomMove).getJumped());
		
		return possibleMoves.get(randomMove);
	}
	
	/**
	 * Returns the minimax val of the given move
	 * 
	 * @param move	the move whose minimax val is evaluated
	 * @return	the minimax val of the given move
	 */
	private static double getMinimaxVal(Move move)
	{
		
		
		return 0;
	}
}
