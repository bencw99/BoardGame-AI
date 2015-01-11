package game.player.ai;

import java.util.ArrayList;

/**
 * A super class for the compressed and expanded versions of the minimax node
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class MinimaxSuperNode
{
	/** The minimax value of this node **/
	private double value;
	
	/** The minimax depth of this minimax node **/
	protected int minimaxDepth;	

	/** The boolean representing turn **/
	boolean thisPlayersTurn;
	
	/** The string identifying this node **/
	protected String identification;
	
	/** The child values of this minimax value **/
	protected ArrayList<MinimaxSuperNode> children;
	
	/**
	 * @return	the value
	 */
	public double getValue()
	{
		return value;
	}
	
	/**
	 * @param value	the value to be set to
	 */
	public void setValue(double value)
	{
		this.value = value;
	}
	
	/**
	 * Loads the children array list
	 */
	public abstract void loadChildren();
	
	/**
	 * @return	the parent of this node
	 */
	public ArrayList<MinimaxSuperNode> getChildren()
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
	 * @return	the turn boolean
	 */
	public boolean getThisPlayersTurn()
	{
		return thisPlayersTurn;
	}
	
	/**
	 * @return the identification
	 */
	public String getIdentification()
	{
		return identification;
	}
}
