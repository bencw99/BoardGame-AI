package game.player.ai;

/**
 * A class representing the value of a minimax node
 * 
 * @author Benjamin Cohen-Wang
 */
public class MinimaxValue extends MinimaxSuperNode
{	
	/** 
	 * Parameterized constructor, initializes this value to the given value
	 * 
	 * @param value	the value of this minimax value
	 */
	public MinimaxValue(double value, int minimaxDepth, MinimaxSuperNode parent, int identification, boolean thisPlayersTurn)
	{
		setValue(value);
		this.minimaxDepth = minimaxDepth;
		this.identification = (parent == null ? "" : parent.getIdentification()) + identification;
		this.thisPlayersTurn = thisPlayersTurn;
	}
	
	/** 
	 * Parameterized constructor, initializes this value to the given value
	 * 
	 * @param value	the value of this minimax value
	 */
	public MinimaxValue(MinimaxSuperNode node)
	{
		setValue(node.getValue());
		this.minimaxDepth = node.minimaxDepth;
		this.identification = node.identification;
		this.thisPlayersTurn = node.thisPlayersTurn;
		this.children = node.children;
	}

	/**
	 * @return	the parent of this node
	 */
	public void loadChildren()
	{
		
	}
}
