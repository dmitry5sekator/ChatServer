package table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;


public class MyMap 
{
	private ArrayList <String> key = new ArrayList<String>();
	private ArrayList <String> value = new ArrayList<String>();
	private ArrayList <String> type = new ArrayList<String>();
	public void add(String key,String value)
	{
		this.key.add(key);
		this.value.add(value);
	}
	public Object getKey(int ind)
	{
		return key.get(ind);
	}
	public Object getValue(int ind)
	{
		return value.get(ind);
	}
	public String getType(int ind)
	{
		return type.get(ind);
	}
	public void setTypes(Connection conn,String tableName)
	{
		try
		{
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
			//////////////////////
			ArrayList <String> COLUMN_NAME = new ArrayList<String>();
			ArrayList <String> TYPE_NAME = new ArrayList<String>();
			while (resultSet.next()) 
			{
				COLUMN_NAME.add(resultSet.getString("COLUMN_NAME"));
				TYPE_NAME.add(resultSet.getString("TYPE_NAME"));
			}
			for(int i = 0;i < key.size();i++)
			{
				for(int x = 0;x < COLUMN_NAME.size();x++)
				{
					if(key.get(i).equals(COLUMN_NAME.get(x)))
					{
						type.add(TYPE_NAME.get(x));
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public int lenght()
	{
		return key.size();
	}
}
