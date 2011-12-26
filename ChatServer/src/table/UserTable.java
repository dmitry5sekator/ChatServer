package table;

import java.sql.Connection;
import java.sql.ResultSet;

public class UserTable extends TableGateWay
{
	String tableName = "users";
	public UserTable(Connection conn) 
	{
		super(conn, "users");
		// TODO Auto-generated constructor stub
	}
	public boolean checkSingIn(int id,String password)
	{
		return false;
	}
	public int returnId(String nick,String password)
	{
		return 0;
	}
	public ResultSet getAllLoginPass()
	{
		return null;
	}
	
}
