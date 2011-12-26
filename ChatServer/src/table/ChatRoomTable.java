package table;

import java.sql.Connection;
import java.sql.ResultSet;

public class ChatRoomTable  extends TableGateWay
{
	String tableName = "chatroom";
	public ChatRoomTable(Connection conn)
	{
		super(conn, "chatroom");
		// TODO Auto-generated constructor stub
	}
	public ResultSet getAllUsersFromChatRoom(int id)
	{
		return null;
	}
	public int returnId(String name,String user_id)
	{
		return 0;
	}
}
