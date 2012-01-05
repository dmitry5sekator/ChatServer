package classes;

import java.util.HashMap;

public class ChatRoom 
{
	private int id;
	private String name;
	private String info;
	private int users_id;
	
	public ChatRoom()
	{}
	public ChatRoom(HashMap <String,String> map)
	{
		this.name = map.get("name");
		this.info = map.get("info");
		this.users_id = Integer.parseInt(map.get("users_id"));
	}
	
	
	public void setName(String name)
	{this.name = name;}
	public void setInfo(String info)
	{this.info = info;}
	public void setUsersId(int users_id)
	{this.users_id = users_id;}
	
	public int getId()
	{return id;}
	public String getName()
	{return name;}
	public String getInfo()
	{return info;}
	public int getUsersId()
	{return users_id;}
}
