package game.player.ai;

import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.Move;
import game.Game.Turn;
import game.piece.Piece;
import game.player.Player;
/**
 * A class representing a node in the minimax algorithm
 * 
 * @author Benjamin Cohen-Wang
 */
public class MinimaxNode 
{
	/** The minimax depth of this minimax node **/
	private int minimaxDepth;
	
	/** The game of this minimax node **/
	private Game game;
	
	/** The array of Arraylists containing the pieces of this grid **/
	ArrayList<ArrayList<Piece>> playerPieces;
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param ame			the game to be set to
	 */
	public MinimaxNode(int minimaxDepth, Game game)
	{
		this.minimaxDepth = minimaxDepth;
		this.game = game;
	}
	
	
	/**
	 * Returns the next node of the given move
	 * 
	 * @return	the minimax node resulting from the given move
	 * @throws IOException 
	 */
	public MinimaxNode getNextNode(Move move) throws IOException
	{
		Game newGame = new Game(game);
		
		if(!(game.getTurn() == Turn.OVER))
		{	
			newGame.getBoard().executeMove(move);
			
			newGame.setTurn(newGame.getTurn().getOther());
		}
		
		return new MinimaxNode(minimaxDepth + 1, newGame);
	}
	
	/**
	 * Returns the possible next moves of this node
	 * 
	 * @return	the array list of edges from this node
	 */
	public ArrayList<Move> getNextMoves()
	{	
		Player currentPlayer = game.getPlayers()[game.getTurn().getVal()];
		
		return currentPlayer.getPossibleMoves();
	}
	
	/**
	 * Returns the loyalty associated with 
	 * 
	 * @return	the loyalty of this node
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * @return the minimaxDepth
	 */
	public int getMinimaxDepth() 
	{
		return minimaxDepth;
	}
}
