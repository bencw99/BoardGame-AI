package game.player.ai;

import java.io.IOException;

import game.Game;
import game.move.Move;
import game.piece.Piece.Loyalty;
import game.player.Player;

/**
 * A dummy player to be used only in the minimax search
 * INCAPABLE OF FUNCTIONING IN A REAL GAME
 * 
 * @author Benjamin Cohen-Wang
 */
public class Dummy extends Player
{
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param pieces	the pieces of this player
	 */
	public Dummy(String name, Loyalty loyalty, Game game)
	{
		super(name, loyalty, game);
	}

	/**
	 * Dummy function, never used
	 */
	public Move getThisTurnMove() throws IOException
	{
		return null;
	}
}
