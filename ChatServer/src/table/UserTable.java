package table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import logger.Logger;

public class UserTable extends TableGateWay
{
	String tableName = "users";
	public UserTable(Connection conn) 
	{
		super(conn, "users");
		// TODO Auto-generated constructor stub
	}
	public synchronized boolean checkSingIn(int id,String password)
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
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
			return false;
		}
		
	}
	public synchronized int returnId(String nick,String password)
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
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
			return 0;
		}
	
	}
	public synchronized ResultSet getAllLoginPass()
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
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
			return null;
		}
		
	}
	
}
