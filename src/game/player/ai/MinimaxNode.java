package game.player.ai;

import java.util.ArrayList;

import game.Game;
import game.Game.Turn;
import game.Move;
import game.board.Board;
import game.piece.Piece.Loyalty;
/**
 * A class representing a node in the minimax algorithm
 * 
 * @author Benjamin Cohen-Wang
 */
public class MinimaxNode 
{
	/** The minimax depth of this minimax node **/
	private int minimaxDepth;
	
	/** The board of this minimax node **/
	private Board board;
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param board			the board to be set to
	 */
	public MinimaxNode(int minimaxDepth, Board board)
	{
		this.minimaxDepth = minimaxDepth;
		this.board = board;
	}
	
	/**
	 * Returns the loyalty associated with 
	 * 
	 * @return	the loyalty of this node
	 */
	public Turn getTurn()
	{
		return board.getGame().getTurn();
	}
	
	/**
	 * Returns the next node of the given move
	 * 
	 * @return	the minimax node resulting from the given move
	 */
	public MinimaxNode getNextNode(Move move)
	{
		return null;
	}
	
	/**
	 * Returns the possible next moves of this node
	 * 
	 * @return	the array list of edges from this node
	 */
	public ArrayList<Move> getNextMoves()
	{
		return null;
	}
	
	/**
	 * @return the minimaxDepth
	 */
	public int getMinimaxDepth() 
	{
		return minimaxDepth;
	}

	/**
	 * @return the board
	 */
	public Board getBoard() 
	{
		return board;
	}
}
