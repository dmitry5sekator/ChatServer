package table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class MessagesTable extends TableGateWay 
{
	Statement stat;
	String tableName = "messages";
	public MessagesTable(Connection conn) {
		super(conn, "messages");
		// TODO Auto-generated constructor stub
	}
	public ResultSet getMessageFromChatRoom(int idRoom,int days)
	{
		try
		{
			
			String SQL = "SELECT * FROM messages WHERE TO_DAYS(NOW()) - TO_DAYS(`messages`.`time`) <= "+days+" AND `messages`.`chatroom_id` = "+idRoom+"";
			Statement stat = conn.createStatement();
			ResultSet set = stat.executeQuery(SQL);
			
			return set;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

}
