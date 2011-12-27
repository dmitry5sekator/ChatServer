package table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MyMap 
{
	private ArrayList<KVT> value = new ArrayList<KVT>();
//	public void add(KVT temp)
//	{
//		value.add(temp);
//	}
	public void add(String key,String value)
	{
		this.value.add(new KVT(key,value));
	}
	public void setTypes(Connection conn,String tableName)
	{
		try
		{
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
			
			ArrayList <String> COLUMN_NAME = new ArrayList<String>();
			ArrayList <String> TYPE_NAME = new ArrayList<String>();
			
			while(resultSet.next())
			{
				COLUMN_NAME.add(resultSet.getString("COLUMN_NAME"));
				TYPE_NAME.add(resultSet.getString("TYPE_NAME"));
			}
			
			for(int i = 0;i < value.size();i++)
			{
				for(int x = 0;x < COLUMN_NAME.size();x++)
				{
					if(value.get(i).key.equals(COLUMN_NAME.get(x)))
					{
						value.get(i).setType(TYPE_NAME.get(x));
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
		return value.size();
	}
	public KVT getValue(int ind)
	{
		return value.get(ind);
	}
}
