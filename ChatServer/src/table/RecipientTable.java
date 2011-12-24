package table;

import java.sql.Connection;
import java.sql.Statement;


public class RecipientTable  extends TableGateWay
{
	Statement stat;
	String tableName = "recipient";
	public RecipientTable(Connection conn) {
		super(conn, "recipient");
		// TODO Auto-generated constructor stub
	}
	
}
