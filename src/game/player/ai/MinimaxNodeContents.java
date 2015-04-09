package game.player.ai;

import game.Game;
import game.board.Board;
import game.board.RectangularBoard;
import game.board.node.Node;
import game.move.Move;
import game.piece.Piece;
import game.player.Player;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class representing the location independent portion of a minimxax node 
 * 
 * @author Benjamin Cohen-Wang
 */
public class MinimaxNodeContents
{
	/** The game of this minimax node **/
	private Game game;
	
	/** The arraylist of moves from this node **/
//	private ArrayList<Move> nextMoves;
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param ame			the game to be set to
	 */
	public MinimaxNodeContents(Game game)
	{
		this.game = game;
	}
	
	/**
	 * Returns the next node of the given move
	 * 
	 * @return	the minimax node resulting from the given move
	 * @throws IOException 
	 */
	public MinimaxNodeContents getNextContents(Move move) throws IOException
	{
		Game newGame = new Game(game);
		
		newGame.getBoard().executeMove(move);
			
		newGame.setTurn(newGame.getTurn().getOther());
		
		return new MinimaxNodeContents(newGame);
	}
	
	/**
	 * Returns the possible next moves of this node
	 * 
	 * @return	the array list of edges from this node
	 */
	public ArrayList<Move> getNextMoves()
	{	
//		loadNextMoves();
//		return nextMoves;
		
		Player currentPlayer = game.getPlayers()[game.getTurn().getVal()];
		return currentPlayer.getPossibleMoves();
	}
	
	/**
	 * Loads the next moves array list
	 */
//	public void loadNextMoves()
//	{
//		if(nextMoves == null)
//		{
//			Player currentPlayer = game.getPlayers()[game.getTurn().getVal()];
//			this.nextMoves = currentPlayer.getPossibleMoves();
//		}
//	}

	/**
	 * Returns the hash code of these minimax node contents, overrides from Object
	 * 
	 *  @return the hash code
	 */
	@Override
	public int hashCode()
	{
		int sum = 0;
		
		for(Node node : game.getBoard().getNodes())
		{
			if(node.getPiece() == null)
			{
				sum += 0;
			}
			else
			{
				sum += node.getPiece().getEnum(); // Zero should be some identifier for this piece
			}
			
			/* Following values subject to testing and change */
			sum *= 31;
			sum %= 104729;
		}
		
		return sum;
	}
	
	/**
	 * Compares these contents with another
	 * 
	 * @param other the contents to be compared to
	 * @return a boolean representing the comparison
	 */
	public boolean equals(Object obj)
	{
		if(obj instanceof MinimaxNodeContents)
		{
			MinimaxNodeContents other = (MinimaxNodeContents) obj;
			return (other.getGame().getTurn().equals(this.getGame().getTurn()) && ((RectangularBoard) other.getBoard()).equals((RectangularBoard) this.getBoard()));
	
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * @return	the game of these contents
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * @return	the board of these contents
	 */
	public Board getBoard()
	{
		return game.getBoard();
	}
}
