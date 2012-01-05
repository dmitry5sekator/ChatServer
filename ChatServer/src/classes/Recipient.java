package classes;

import java.util.HashMap;
import java.util.Map;

public class Recipient 
{
	private int id;
	private int users_id;
	private int chatroom_id;
	public Recipient()
	{}
	public Recipient(HashMap <String,String> map)
	{
		this.chatroom_id = Integer.parseInt(map.get("chatroom_id"));
		this.users_id = Integer.parseInt(map.get("users_id"));
	}
	

	public void setUsersId(int users_id)
	{this.users_id = users_id;}
	public void setChatroomId(int chatroom_id)
	{this.chatroom_id = chatroom_id;}
	
	public int getId()
	{return id;}
	public int getUsersId()
	{return users_id;}
	public int getChatroomId()
	{return chatroom_id;}
	
	public Map<String,String> toMap()
	{
		Map <String,String> temp = new HashMap<String,String>();
		temp.put("users_id", Integer.toString(this.users_id));
		temp.put("chatroom_id", Integer.toString(this.chatroom_id));
		return temp;
	}
}
