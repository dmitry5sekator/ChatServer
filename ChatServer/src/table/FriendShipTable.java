package table;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;

public class FriendShipTable  extends TableGateWay 
{

	public FriendShipTable(Connection conn) 
	{
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResultSet SELECT(String query) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int UPDATE(String query) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int DELETE(String query) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int CREATE_TABLE(String query) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
