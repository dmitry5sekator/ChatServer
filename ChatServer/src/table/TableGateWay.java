package table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import logger.Logger;


public abstract class TableGateWay 
{
	protected Connection conn;
	private String tableName;
	public TableGateWay(Connection conn,String tableName)
	{
		this.conn = conn;
		this.tableName = tableName;
	}
	public synchronized int delete(int id)
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "DELETE FROM"+tableName+"WHERE id = "+id+"";
			return stat.executeUpdate(SQL);
		}
		catch(Exception e)
		{
			try{Logger.writeEvent(e.toString());}catch(Exception ex){ex.printStackTrace();}
			e.printStackTrace();
			return 0;
		}
	}
	public synchronized int insert(Map<String,String> map)
	{
		try
		{
			String getFields = "(";
			String getValues = " VALUES (";

			for (Map.Entry<String,String> entry : map.entrySet()){
				 
					getFields += "`" + entry.getKey() + "`,";
					getValues += "'" + entry.getValue() + "',";
				}

			getFields = getFields.substring(0, getFields.length()-1);
			getFields += ")";
			
			getValues = getValues.substring(0, getValues.length()-1);
			getValues += ")";
			
			Statement stat = conn.createStatement();
			String SQL = "INSERT INTO " + tableName +" "+ getFields+getValues+"";
			System.out.println(SQL);
			return stat.executeUpdate(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	public synchronized int update(Map<String,String> map,String field,Object value)
	{
		try
		{
			String SQL = "UPDATE " +tableName+" SET ";
			for (Map.Entry<String,String> entry : map.entrySet()){
				 
				SQL += "`" + entry.getKey() + "`=";
				SQL += "'" + entry.getValue() + "',";
			}
			SQL = SQL.substring(0, SQL.length()-1);
			SQL = " WHERE `" + field + "` = '"+value+"'";
			Statement stat = conn.createStatement();
			return stat.executeUpdate(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	public synchronized ResultSet select()
	{
		try
		{
			String SQL = "SELECT * FROM "+tableName;
			Statement stat = conn.createStatement();
			return stat.executeQuery(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
