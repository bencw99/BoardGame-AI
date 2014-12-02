package game.player.ai;

import game.Game;
import game.Move;
import game.player.Player;
import game.player.Player.State;

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
	
	/** The arraylist of children of this node **/
	private ArrayList<MinimaxNodeContents> children;
	
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
	public MinimaxNodeContents getNextNode(Move move) throws IOException
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
		Player currentPlayer = game.getPlayers()[game.getTurn().getVal()];
		
		ArrayList<Move> possibleMoves = currentPlayer.getPossibleMoves();
		
		if(possibleMoves.isEmpty())
		{
			currentPlayer.setState(State.DEFEATED);
		}
		
		return possibleMoves;
	}
	
	/**
	 * Loads the children array list
	 */
	public void loadChildren()
	{
		if(children != null)
		{
			this.children = new ArrayList<MinimaxNodeContents>();
			for(Move move : getNextMoves())
			{
				try
				{
					children.add(getNextNode(move));
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
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
		return (other.getGame().getTurn().equals(this.getGame().getTurn()) && other.getGame().getBoard().equals(this.getGame().getBoard()));
	}
	
	/**
	 * @return	the game of this node
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * @return	the parent of this node
	 */
	public ArrayList<MinimaxNodeContents> getChildren()
	{
		loadChildren();
		return children;
	}
}
