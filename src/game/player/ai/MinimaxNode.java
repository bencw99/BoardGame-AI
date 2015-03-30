package game.player.ai;

import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.board.Board;
import game.move.Move;

/**
 * A class representing a node in the minimax algorithm
 * 
 * @author Benjamin Cohen-Wang
 */
public class MinimaxNode extends MinimaxSuperNode
{	
	/** The move relating this node to the parent **/
	private Move move;
	
	/** The contents of this minimax node **/
	private MinimaxNodeContents contents;
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param ame			the game to be set to
	 */
	public MinimaxNode(int minimaxDepth, Game game, MinimaxSuperNode parent, Move move, int identification, boolean thisPlayersTurn)
	{
		this.minimaxDepth = minimaxDepth;
		this.contents = new MinimaxNodeContents(game);
		this.move = move;
		this.identification = (parent == null ? "" : parent.getIdentification()) + identification;
		this.thisPlayersTurn = thisPlayersTurn;
	}
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param ame			the game to be set to
	 */
	public MinimaxNode(int minimaxDepth, MinimaxNodeContents contents, MinimaxNode parent, Move move, int identification, boolean thisPlayersTurn)
	{
		this.minimaxDepth = minimaxDepth;
		this.contents = contents;
		this.move = move;
		this.identification = (parent == null ? "" : parent.getIdentification()) + identification;
		this.thisPlayersTurn = thisPlayersTurn;
	}
	
	/**
	 * Returns the next node of the given move
	 * 
	 * @return	the minimax node resulting from the given move
	 * @throws IOException 
	 */
	public MinimaxNode getNextNode(Move move) throws IOException
	{
		MinimaxNodeContents newContents = contents.getNextContents(move);

		return new MinimaxNode(minimaxDepth + 1, newContents, this, move, 0, !getThisPlayersTurn());
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
			this.children = new ArrayList<MinimaxSuperNode>();
			
			int currentChildID = 0;
			
			for(Move nextMove : contents.getNextMoves())
			{
				try
				{
					children.add(new MinimaxNode(minimaxDepth + 1, contents.getNextContents(nextMove), this, nextMove, currentChildID, !getThisPlayersTurn()));
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
				currentChildID ++;
			}
		}
	}
	
	/**
	 * Compares this node with another
	 * 
	 * @param other the node to be compared to
	 * @return a boolean representing the comparison
	 */
	public boolean equals(Object obj)
	{
		if(obj instanceof MinimaxNode)
		{
			MinimaxNode other = (MinimaxNode) obj;
			return this.contents.equals(other.contents);
			
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * @return	the contents of this node
	 */
	public MinimaxNodeContents getContents()
	{
		return contents;
	}
	
	/**
	 * @return	the game of this node
	 */
	public Game getGame()
	{
		return contents.getGame();
	}
	
	/**
	 * @return	the board of this node
	 */
	public Board getBoard()
	{
		return contents.getGame().getBoard();
	}
	
	/**
	 * @return the move to this minimax node
	 */
	public Move getMove()
	{
		return move;
	}
}
