package classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Message 
{
	private int id;
	private String message;
	private int users_id;
	private int chatroom_id;
	private String time;
	
	public Message(HashMap <String,String> map)
	{
		this.message = map.get("message");
		this.users_id = Integer.parseInt(map.get("message"));
		this.chatroom_id = Integer.parseInt(map.get("chatroom_id"));
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
	    this.time =  sdf.format(cal.getTime());

	}
	
	public void setMessage(String message)
	{this.message = message;}
	public void setUsersId(int users_id)
	{this.users_id = users_id;}
	public void setChatroomId(int chatroom_id)
	{this.chatroom_id = chatroom_id;}
	public void setTime(String time)
	{this.time = time;}
	
	public int getId()
	{return id;}
	public String getMessage()
	{return message;}
	public int getUsersId()
	{return users_id;}
	public int getChatroomId()
	{return chatroom_id;}
	public String getTime()
	{return time;}
}
