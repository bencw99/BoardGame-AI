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
	public MinimaxValue(double value, int minimaxDepth)
	{
		setValue(value);
		this.minimaxDepth = minimaxDepth;
	}

	/**
	 * @return	the parent of this node
	 */
	public void loadChildren()
	{
		
	}
}
