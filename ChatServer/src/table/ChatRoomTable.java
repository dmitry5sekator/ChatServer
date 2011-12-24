package table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ChatRoomTable extends TableGateWay
{
	Statement stat;
	String tableName = "chatroom";
	public ChatRoomTable(Connection conn) 
	{
		super(conn,"chatroom");
		// TODO Auto-generated constructor stub
	}
	public ResultSet getAllUsersFromChatRoom(int id)
	{
		try
		{
			String SQL = "SELECT chatroom.id,`chatroom`.`name` " +
							"FROM users,recipient,chatroom "+
					"WHERE users.id = recipient.users_id AND "+
							"recipient.chatroom_id = chatroom.id AND "+
					"`chatroom`.`id` = "+id+"";
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
	public ResultSet getAdminRoom(int id)
	{
		try
		{
			String SQL = "SELECT chatroom.id,`chatroom`.`name` " +
							"FROM users,recipient,chatroom "+
					"WHERE users.id = recipient.users_id AND "+
							"recipient.chatroom_id = chatroom.id AND "+
					"`chatroom`.`users_id` = `users`.`id` "+
					"`chatroom`.`id` = "+id+"";
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
	public int deleteAllUsersFromChatRoom(int id)
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "DELETE FROM recipient WHERE chatroom_id = "+id+"";
			System.out.println(SQL);
			return stat.executeUpdate(SQL);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
		
	}
	public int deleteUserFromChatRoom(int idUser,int idRoom)
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "DELETE FROM recipient WHERE chatroom_id = "+idRoom+" AND users_id ="+idUser+"";
			System.out.println(SQL);
			return stat.executeUpdate(SQL);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
		
	}
	public int addToChatRoom(int idUser,int idRoom)
	{
		try
		{
			Statement stat = conn.createStatement();
			String SQL = "INSERT INTO `chat_db`.`recipient` (`users_id`, `chatroom_id`) VALUES ("+idUser+", "+idRoom+");";
			System.out.println(SQL);
			return stat.executeUpdate(SQL);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
		
	}
	
}
