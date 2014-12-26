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
	
	/** The value of this minimax node **/
	private double value;
	
	/** The parent node of this minimax node **/
	private MinimaxNode parent;
	
	/** The string identifying this node **/
	private String identification;
	
	/** The arraylist of children of this node **/
	private ArrayList<MinimaxNode> children;
	
	/** The contents of this minimax node **/
	private MinimaxNodeContents contents;
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param ame			the game to be set to
	 */
	public MinimaxNode(int minimaxDepth, Game game, MinimaxNode parent, int identification)
	{
		this.minimaxDepth = minimaxDepth;
		this.contents = new MinimaxNodeContents(game);
		this.parent = parent;
		this.identification = (parent == null ? "" : parent.getIdentification()) + identification;
	}
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param ame			the game to be set to
	 */
	public MinimaxNode(int minimaxDepth, MinimaxNodeContents contents, MinimaxNode parent, int identification)
	{
		this.minimaxDepth = minimaxDepth;
		this.contents = contents;
		this.parent = parent;
		this.identification = (parent == null ? "" : parent.getIdentification()) + identification;
	}
	
	/**
	 * Returns the next node of the given move
	 * 
	 * @return	the minimax node resulting from the given move
	 * @throws IOException 
	 */
	public MinimaxNode getNextNode(Move move) throws IOException
	{
		MinimaxNodeContents newContents = contents.getNextNode(move);
		
		return new MinimaxNode(minimaxDepth + 1, newContents, this, 0);
	}
	
	/**
	 * Returns the possible next moves of this node
	 * 
	 * @return	the array list of edges from this node
	 */
	public ArrayList<Move> getNextMoves()
	{	
		return contents.getNextMoves();
	}
	
	/**
	 * Loads the children array list
	 */
	public void loadChildren()
	{
		if(children == null)
		{
			this.children = new ArrayList<MinimaxNode>();
			
			int currentChildID = 0;
			
			for(MinimaxNodeContents childContents : contents.getChildren())
			{
				children.add(new MinimaxNode(minimaxDepth + 1, childContents, this, currentChildID));
				
				currentChildID ++;
			}
		}
	}
	
	public boolean equals(MinimaxNode other)
	{
		return (other.getGame().getTurn().equals(this.getGame().getTurn()) && other.getGame().getBoard().equals(this.getGame().getBoard()));
	}
	
	/**
	 * @param value the value this minimax node is set to have
	 */
	public void setValue(double value)
	{
		this.value = value;
	}
	
	/**
	 * @return	the game of this node
	 */
	public Game getGame()
	{
		return contents.getGame();
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
		loadChildren();
		return children;
	}
	
	/**
	 * @return the minimaxDepth
	 */
	public int getMinimaxDepth() 
	{
		return minimaxDepth;
	}
	
	/**
	 * @return the identification
	 */
	public String getIdentification()
	{
		return identification;
	}
	
	/**
	 * @return the value of this minimax node
	 */
	public double getValue()
	{
		return value;
	}
}
