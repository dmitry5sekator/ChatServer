package table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import logger.Logger;

public class ChatRoomTable  extends TableGateWay
{
	String tableName = "chatroom";
	public ChatRoomTable(Connection conn)
	{
		super(conn, "chatroom");
		// TODO Auto-generated constructor stub
	}
	public  synchronized ResultSet getAllUsersFromChatRoom(int id)
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "SELECT users.id,users.nick FROM chatroom,recipient,users"  +
						" WHERE chatroom.id = recipient.chatroom_id AND" +
						" recipient.users_id = users.id AND" +
						" chatroom.id = "+id+";";
			return stat.executeQuery(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
		}
		return null;
	}
	public synchronized int returnId(String name,String user_id)
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "SELECT id FROM chatroom WHERE name = '"+name+"' AND users_id = "+user_id+";";
			ResultSet set = stat.executeQuery(SQL);
			if(set.next())
			{
				return set.getInt("id");
			}
			else
			{
				return 0;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
			return 0;
			
		}
		
	}
}
