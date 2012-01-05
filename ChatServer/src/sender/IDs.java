package sender;

public class IDs 
{
	public int idTable;
	public int idArray;
	public IDs(int idTable,int idArray)
	{
		this.idArray = idArray;
		this.idTable = idTable;
	}
	public IDs(int idTable)
	{
		this.idArray = -777;
		this.idTable = idTable;
	}
	public int getIdArray()
	{
		return idArray;
	}
	public int getIdTable()
	{
		return idArray;
	}
	
	public void setIdArray(int idArray)
	{
		this.idArray = idArray;
	}
	public void setIdTable(int idTable)
	{
		this.idTable = idTable;
	}
}
