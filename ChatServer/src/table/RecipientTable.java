package table;

import java.sql.Connection;
import java.sql.Statement;

public class RecipientTable   extends TableGateWay
{
	String tableName = "recipient";
	public RecipientTable(Connection conn) 
	{
		super(conn, "recipient");
		// TODO Auto-generated constructor stub
	}
	public synchronized int getOverFromRoom(int users_id,int chatroom_id)
	{
		////DELETE FROM recipient WHERE users_id = 4 AND chatroom_id = 1
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "DELETE FROM "+tableName+" WHERE users_id = '"+users_id+"' AND chatroom_id = '" +chatroom_id+"'";
			return stat.executeUpdate(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

}
