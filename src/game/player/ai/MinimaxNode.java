package game.player.ai;

import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.Move;
import game.Game.Turn;
import game.piece.Piece;
import game.player.Player;
import game.player.Player.State;
/**
 * A class representing a node in the minimax algorithm
 * 
 * @author Benjamin Cohen-Wang
 */
public class MinimaxNode 
{
	/** The minimax depth of this minimax node **/
	private int minimaxDepth;
	
	/** The parent node of this minimax node **/
	private MinimaxNode parent;
	
	/** The arraylist of children of this node **/
	private ArrayList<MinimaxNode> children;
	
	/** The game of this minimax node **/
	private Game game;
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param ame			the game to be set to
	 */
	public MinimaxNode(int minimaxDepth, Game game, MinimaxNode parent)
	{
		this.minimaxDepth = minimaxDepth;
		this.game = game;
		this.parent = parent;
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
		
		newGame.getBoard().executeMove(move);
			
		newGame.setTurn(newGame.getTurn().getOther());
		
		return new MinimaxNode(minimaxDepth + 1, newGame, this);
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
		this.children = new ArrayList<MinimaxNode>();
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
	
	public boolean equals(MinimaxNode other)
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
	public MinimaxNode getParent()
	{
		return parent;
	}
	
	/**
	 * @return	the parent of this node
	 */
	public ArrayList<MinimaxNode> getChildren()
	{
		if(children == null)
		{
			loadChildren();
		}
		return children;
	}
	
	/**
	 * @return the minimaxDepth
	 */
	public int getMinimaxDepth() 
	{
		return minimaxDepth;
	}
}
