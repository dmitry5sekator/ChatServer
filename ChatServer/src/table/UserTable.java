package table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "SELECT nick FROM users WHERE id = "+id+" AND password = '"+password+"'";
			ResultSet set = stat.executeQuery(SQL);
			if(set.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	public int returnId(String nick,String password)
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "SELECT id FROM users WHERE nick = '"+nick+"' AND password = '"+password+"';";
			ResultSet set = stat.executeQuery(SQL);
			if(set.next())
			{
				return set.getInt("id");
			}
			else
			{
				return 0;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	
	}
	public ResultSet getAllLoginPass()
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "SELECT nick,password FROM users;";
			return stat.executeQuery(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
}