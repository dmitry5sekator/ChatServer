package table;

import java.sql.Connection;

public class RecipientTable   extends TableGateWay
{
	String tableName = "recipient";
	public RecipientTable(Connection conn) 
	{
		super(conn, "recipient");
		// TODO Auto-generated constructor stub
	}

}
