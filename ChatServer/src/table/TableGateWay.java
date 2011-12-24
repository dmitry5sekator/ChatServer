package table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;



public abstract class TableGateWay
{
    protected static Connection conn;
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
			String SQL = "DELETE FROM "+tableName+" WHERE id = "+id+"";
			System.out.println(SQL);
			return stat.executeUpdate(SQL);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
    }
    public int insert(MyMap map)
    {
		map.setTypes(conn, tableName);
    	String getFields = "(";
		String getVelues = " VALUES (";
		try 
		{
			for(int i = 0;i < map.lenght();i++)
			{
					getFields += "`" + map.getKey(i) + "`,";
					if(map.getType(i).equals("VARCHAR"))
					{
						getVelues += "'" + map.getValue(i).toString() +"',";
					}
					else if(map.getType(i).equals("DATETIME"))
					{
						getVelues += "'" + map.getValue(i).toString() +"',";
					}
					else
					{
						getVelues += map.getValue(i) + ",";
					}
			}
			getFields = getFields.substring(0, getFields.length()-1);
			getFields += ")";
			getVelues = getVelues.substring(0, getVelues.length()-1);
			getVelues += ")";
			Statement stat = conn.createStatement();
			String SQL = "INSERT INTO "+tableName+" "+getFields+getVelues+"";
			System.out.println(SQL);
			return stat.executeUpdate(SQL);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return 0;
		} 
		finally 
		{
			System.out.println(getFields + "   " + getVelues);
		}
    }
    public int update(MyMap map,String field,Object value)
    {
    	map.setTypes(conn, tableName);
    	String SQL = "UPDATE "+tableName+" SET ";
    	try 
		{
			for(int i = 0;i < map.lenght();i++)
			{
				SQL += "`" + map.getKey(i) + "`=";
					if(map.getType(i).equals("VARCHAR"))
					{
						SQL += "'" + map.getValue(i).toString() +"',";
					}
					else if(map.getType(i).equals("DATETIME"))
					{
						SQL += "'" + map.getValue(i).toString() +"',";
					}
					else
					{
						SQL += map.getValue(i) + ",";
					}
			}
			SQL = SQL.substring(0, SQL.length()-1);
			SQL += " WHERE `"+field + "` = '"+value+"'";
			Statement stat = conn.createStatement();
			System.out.println(SQL);
			return stat.executeUpdate(SQL);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return 0;
		} 
    }
    public ResultSet select()
	{
		try
		{
		String SQL = "SELECT * FROM " +tableName+ "";
		Statement stat = conn.createStatement();
		return stat.executeQuery(SQL);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
}