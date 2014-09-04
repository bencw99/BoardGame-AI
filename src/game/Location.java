package game;

/**
 * The class representing locations on the board
 * 
 * @author Benjamin Cohen-Wang
 */
public class Location 
{
	/** The row of this location **/
	private int row;
	
	/** The column of this location **/
	private int col;
	
	/**
	 * Parameterized constructor, initializes row and column to given values
	 * 
	 * @param row	the row of this location
	 * @param col	the column of this location
	 */
	public Location(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	public int getRow() 
	{
		return row;
	}

	public int getCol() 
	{
		return col;
	}

	public void setRow(int row) 
	{
		this.row = row;
	}

	public void setCol(int col) 
	{
		this.col = col;
	}
	
	
}
