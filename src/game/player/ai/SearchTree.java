package game.player.ai;

import java.util.ArrayList;

/**
 * A class representing a search tree of the minimax algorithm
 * 
 * @author Benjamin Cohen-Wang
 */
public class SearchTree
{
	/** The root node of this minimax search tree **/
	private MinimaxNode root;
	
	/** The depth of this search tree **/
	private int depth;
	
	/**
	 * Parameterized constructor, initializes search tree to given root node
	 * 
	 * @param root	the root node this tree is initialized to
	 */
	public SearchTree(MinimaxNode root)
	{
		this.root = root;
	}
	
	/**
	 * Executes the next iteration of iterative deepening
	 */
	public void increaseDepth()
	{
		
	}
	
	/**
	 * 
	 * @return
	 */
	public MinimaxNode getNode(String identification)
	{
		MinimaxNode currentNode = root;
		
		for(int i = 0; i < identification.length(); i ++)
		{
			int currentIdentification = (int)identification.charAt(i) - 48;
			
			currentNode = currentNode.getChildren().get(currentIdentification);
		}
		
		return currentNode;
	}
}
