package table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;


public abstract class TableGateWay 
{
	protected Connection conn;
	private String tableName;
	public TableGateWay(Connection conn,String tableName)
	{
		this.conn = conn;
		this.tableName = tableName;
	}
	public int delete(int id)
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "DELETE FROM"+tableName+"WHERE id = "+id+"";
			return stat.executeUpdate(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	public int insert(MyMap map)
	{
		try
		{
			map.setTypes(conn, tableName);
			String getFields = "(";
			String getValues = " VALUES (";

			for(int i = 0;i < map.lenght();i++)
			{
				getFields += "`" + map.getValue(i).key + "`,";
				getValues += "'" + map.getValue(i).value + "',";
			}
			
			getFields = getFields.substring(0, getFields.length()-1);
			getFields += ")";
			
			getValues = getValues.substring(0, getValues.length()-1);
			getValues += ")";
			
			Statement stat = conn.createStatement();
			String SQL = "INSERT INTO" + tableName +" "+ getFields+getValues+"";
			return stat.executeUpdate(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	public int update(MyMap map,String field,Object value)
	{
		try
		{
			map.setTypes(conn, tableName);
			String SQL = "UPDATE " +tableName+" SET ";
			for(int i = 0;i < map.lenght();i++)
			{
				SQL += "`" + map.getValue(i).key + "`=";
				SQL += "'" + map.getValue(i).value + "',";
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
	public ResultSet select()
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
