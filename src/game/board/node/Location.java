package game.board.node;

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
	 * Parameterized constructor, initializes row and column to given location values
	 * 
	 * @param loc	the location whose value is constructed
	 */
	public Location(Location loc)
	{
		this.row = loc.row;
		this.col = loc.col;
	}
	
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

	public boolean equals(Location other)
	{
		if(this.row == other.row && this.col == other.col)
		{
			return true;
		}
		else
		{
			return false;
		}
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
	
	public String toString()
	{
		return ("(" + getRow() + ", " + getCol() + ")");
	}
}
