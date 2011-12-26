package table;

import java.sql.Connection;
import java.sql.ResultSet;

public class MessagesTable  extends TableGateWay
{
	String tableName = "messages";
	public MessagesTable(Connection conn) 
	{
		super(conn, "messages");
		// TODO Auto-generated constructor stub
	}
	public ResultSet getMessageFromChatRoom(int id,int days)
	{
		return null;
	}

}
