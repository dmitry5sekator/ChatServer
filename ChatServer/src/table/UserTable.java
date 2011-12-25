package table;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;



public class UserTable extends TableGateWay
{
	Statement stat;
	String tableName = "users";
	public UserTable(Connection conn) 
	{
		super(conn,"users");
		// TODO Auto-generated constructor stub
	}
	public int returnId(String nick,String pass)
	{
		try
		{
			String id = "";
			String SQL = "SELECT id FROM " +tableName+ " WHERE `nick` = '"+nick+"' AND `password` = '"+pass+"'";
			System.out.println(SQL);
			Statement stat = conn.createStatement();
			ResultSet set = stat.executeQuery(SQL);
			while(set.next())
			{
				id = set.getString("id");
			}
			return Integer.parseInt(id);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
		//return 0;
	}
	public boolean checkSingIn(int id,String pass)
	{
		try
		{
			String temp = "";
			String SQL = "SELECT id FROM " +tableName+ " WHERE `id` = '"+id+"' AND `password` = '"+pass+"'";
			System.out.println(SQL);
			Statement stat = conn.createStatement();
			ResultSet set = stat.executeQuery(SQL);
			
			while(set.next())
			{
				temp = set.getString("id");
			}
			System.out.println(temp);
			if(temp == "")
			{
				return false;
			}
			else
			{
				return true;
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	public String[] getUser(int id)
	{
		try
		{
			String nick = "";
			String info = "";
			String SQL = "SELECT nick,info FROM " +tableName+ " WHERE `id` = '"+id+"'";
			System.out.println(SQL);
			Statement stat = conn.createStatement();
			ResultSet set = stat.executeQuery(SQL);
			while(set.next())
			{
				nick = set.getString("nick");
				info = set.getString("info");
			}
			return new String[]{nick,info};
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public ResultSet getAllChatRoom_iIn(int id)
	{
		//SELECT users.nick,`chatroom`.`name` FROM users,recipient,chatroom WHERE users.id = recipient.users_id AND
			//	recipient.chatroom_id = chatroom.id AND
				//`users`.`nick` = 'DonKarleon';
		try
		{
			String SQL = "SELECT chatroom.id,`chatroom`.`name` " +
							"FROM users,recipient,chatroom "+
					"WHERE users.id = recipient.users_id AND "+
							"recipient.chatroom_id = chatroom.id AND "+
					"`users`.`id` = "+id+"";
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
	public ResultSet getAllChatRoom_iAdmin(int id)
	{
		try
		{
			String SQL = "SELECT `chatroom`.`id`,`chatroom`.`name` " +
					"FROM users,recipient,chatroom "+
					"WHERE users.id = recipient.users_id AND "+				
						"recipient.chatroom_id = chatroom.id AND "+
						"`chatroom`.`users_id` = `users`.`id` AND "+
				"`users`.`id` = "+id+"";
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
	public ResultSet getAllLoginPass()
	{
		try
		{
			String SQL = "SELECT nick,password " +
					"FROM users ";
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