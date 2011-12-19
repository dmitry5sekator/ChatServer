package table;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;

public abstract class TableGateWay 
{
	Connection conn;
	public TableGateWay(Connection conn)
	{
		this.conn = conn;
	}
}
