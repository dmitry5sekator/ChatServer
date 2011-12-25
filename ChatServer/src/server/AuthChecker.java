package server;

import java.lang.reflect.Method;
import java.util.ArrayList;

import our.Response;
import our.UserRequest;
import controllers.Route;

import table.UserTable;

public class AuthChecker 
{
	private static ArrayList<LogPass> r = new ArrayList<LogPass>();
	
	public void addLogPass(String nick,String pass)
	{
		//
		r.add(new LogPass(nick,pass));
	}
	public static boolean checkAuth(String nick,String pass)
	{
		for(LogPass lp : r)
		{
			if(lp.check(nick, nick))
			{
				
				return true;
			}
		}
		return false;
	}
	
}
