package sender;

import java.net.Socket;

public class SenderMap {
	public int idTable;
	public int idArray;
	public Socket s;
	public SenderMap(int idTable,int idArray,Socket s)
	{
		this.idArray = idArray;
		this.idTable = idTable;
		this.s = s;
	}
	public SenderMap(int idTable,Socket s)
	{
		this.idArray = -777;
		this.idTable = idTable;
		this.s = s;
	}
	public SenderMap(int idTable)
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
