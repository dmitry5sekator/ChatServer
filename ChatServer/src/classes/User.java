package classes;

import java.util.HashMap;
import java.util.Map;

public class User 
{
	private int id;
	private String nick;
	private String password;
	private String info;
	public User()
	{}
	public User(HashMap <String,String> map)
	{
		
		this.info = map.get("info");
		this.password =map.get("password");
		this.nick = map.get("nick");
	}
	
	
	public void setNick(String nick)
	{this.nick = nick;}
	public void setPass(String password)
	{this.password = password;}
	public void setInfo(String info)
	{this.info = info;}
	
	public int getId()
	{return id;}
	public String getNick()
	{return nick;}
	public String getPass()
	{return password;}
	public String getInfo()
	{return info;}
	
	public Map<String,String> toMap()
	{
		Map <String,String> temp = new HashMap<String,String>();
		temp.put("nick", this.nick);
		temp.put("password", this.password);
		temp.put("info", this.info);
		return temp;
	}
}
