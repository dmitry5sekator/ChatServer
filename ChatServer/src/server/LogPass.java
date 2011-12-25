package server;

import java.util.ArrayList;

public class LogPass 
{
	private String login = "";
	private String password = "";
	public LogPass(String login,String password)
	{
		this.login = login;
		this.password = password;
	}
	public boolean check(String log,String pass)
	{
			if(login.equals(log))
			{
				if(password.equals(pass))
				{
					return true;
				}
				
			}
		return false;
	}
}
