package table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import logger.Logger;

public class MessagesTable  extends TableGateWay
{
	String tableName = "messages";
	public MessagesTable(Connection conn) 
	{
		super(conn, "messages");
		// TODO Auto-generated constructor stub
	}
	public synchronized ResultSet getMessageFromChatRoom(int id,int days)
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "SELECT * FROM messages WHERE chatroom_id = "+id+";";
			return stat.executeQuery(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
			return null;
		}
	}

}
