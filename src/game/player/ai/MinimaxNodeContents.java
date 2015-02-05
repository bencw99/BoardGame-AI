package game.player.ai;

import game.board.Board;
import game.move.Move;
import game.piece.Piece.Loyalty;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class representing the location independent portion of a minimxax node 
 * 
 * @author Benjamin Cohen-Wang
 */
public class MinimaxNodeContents
{
//	/** The game of this minimax node **/
//	private Game game;
	
	/** The board of these minimax contents **/
	private Board board;
	
	/** THe turn of these minimax contents **/
	private Loyalty turn;
	
	/** The arraylist of moves from this node **/
	private ArrayList<Move> nextMoves;
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param ame			the game to be set to
	 */
	public MinimaxNodeContents(Board board, Loyalty turn)
	{
		this.board = board;
		this.turn = turn;
	}
	
	/**
	 * Returns the next node of the given move
	 * 
	 * @return	the minimax node resulting from the given move
	 * @throws IOException 
	 */
	public MinimaxNodeContents getNextContents(Move move) throws IOException
	{
		Board newBoard = this.board.clone(null);
		
		Loyalty newTurn = this.turn.getOther();
		
		newBoard.executeMove(move);
		
		return new MinimaxNodeContents(newBoard, newTurn);
	}
	
	/**
	 * Returns the possible next moves of this node
	 * 
	 * @return	the array list of edges from this node
	 */
	public ArrayList<Move> getNextMoves()
	{	
		loadNextMoves();
		return nextMoves;
	}
	
	/**
	 * Loads the next moves array list
	 */
	public void loadNextMoves()
	{
		if(nextMoves == null)
		{
			this.nextMoves = board.getPossibleMoves(turn);
		}
	}
	
	/**
	 * Compares these contents with another
	 * 
	 * @param other the contents to be compared to
	 * @return a boolean representing the comparison
	 */
	public boolean equals(MinimaxNodeContents other)
	{
		return (other.getTurn().equals(this.getTurn()) && other.getBoard().equals(this.getBoard()));
	}
	
//	/**
//	 * @return	the game of these contents
//	 */
//	public Game getGame()
//	{
//		return game;
//	}
	
	/**
	 * @return	the turn of these contents
	 */
	public Loyalty getTurn()
	{
		return turn;
	}
	
	/**
	 * @return	the board of these contents
	 */
	public Board getBoard()
	{
		return board;
	}
}
