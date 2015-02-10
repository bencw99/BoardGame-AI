package game.player;
import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.board.RectangularBoard;
import game.move.Move;
import game.piece.Piece.Loyalty;

/**
 * A class representing a Player associated with a checkers game
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class Player
{	
	/** The loyalty of this player **/
	private Loyalty loyalty;
	
	/** The game this player belongs to **/
	private Game game;
	
	/**The state of this player **/
	private boolean defeated;
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param pieces	the pieces of this player
	 */
	public Player(String name, Loyalty loyalty, Game game)
	{
		this.loyalty = loyalty;
		this.defeated = false;
		this.game = game;
	}
	
	/**
	 * Returns the possible moves of this player
	 * 
	 * @return	the arrayList of possible moves of this player
	 */
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> possibleMoves = game.getBoard().getPossibleMoves(loyalty);
		
		if(possibleMoves.isEmpty())
		{
			setDefeated(true);
		}
		
		return possibleMoves;
	}
	
	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 * @throws IOException 
	 */
	public abstract Move getThisTurnMove() throws IOException;
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return null;
	}
	
	/**
	 * @return the state
	 */
	public boolean isDefeated()
	{
		return defeated;
	}

	/**
	 * @return the loyalty
	 */
	public Loyalty getLoyalty()
	{
		return loyalty;
	}
	
	/**
	 * @return the game
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * @return the board
	 */
	public RectangularBoard getBoard()
	{
		return (RectangularBoard) game.getBoard();
	}
	
	/**
	 * @param state the state to set
	 */
	public void setDefeated(boolean defeated)
	{
		this.defeated = defeated;
	}
}
