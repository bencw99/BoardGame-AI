package game.player.ai;

/**
 * A class representing a search tree of the minimax algorithm
 * 
 * @author Benjamin Cohen-Wang
 */
public class SearchTree
{
	/** The root node of this minimax search tree **/
	private MinimaxNode root;
	
	/**
	 * Parameterized constructor, initializes search tree to given root node
	 * 
	 * @param root	the root node this tree is initialized to
	 */
	public SearchTree(MinimaxNode root)
	{
		this.root = root;
	}
	
	
}
