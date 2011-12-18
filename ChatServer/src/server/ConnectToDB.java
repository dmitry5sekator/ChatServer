package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.*;

import our.Response;
import our.ResponseCodes;


public class ConnectToDB {
	private static Statement stat = null;
	private static Connection conn = null;
	private static ResultSet result = null;
	private static String URL = null;
	private static String nick = null;
	private static String pass = null;
	private static void getAuth()
	{
		try
		{
			RandomAccessFile r = new RandomAccessFile("key.auth","r");
			try
			{
				URL = r.readLine();
				nick = r.readLine();
				pass = r.readLine();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void registerUser(String nickName,String password,String info)
	{
		try
		{
			getAuth();
			conn = DriverManager.getConnection(URL, nick, pass);
			stat = conn.createStatement();
			String command = "SELECT nickName FROM users";
			result = stat.executeQuery(command);
			boolean ok = true;
			while(result.next())
			{
				String Nick = result.getString("nickName");
				if(Nick.equals(nickName))
				{
					 Response e = new Response();
					 e.setResponseCode(ResponseCodes.UserExists);
					 ProcessingOfClient.setHttpResponce(e);
					 ok = false;
					 break;
				}
			}
			if(ok)
			{
				command = "INSERT INTO users (nickName, password, info) VALUES ('"+nickName+"', '"+password+"', '"+info+"')";
				stat.executeUpdate(command);
				Response e = new Response();
				e.setResponseCode(ResponseCodes.UserAdded);
				ProcessingOfClient.setHttpResponce(e);
			}
		}
		catch(SQLException ex)
		{
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	public static void signIn(String nickName,String password,Response responce)
	{
		try
		{
			getAuth();
			conn = DriverManager.getConnection(URL,nick,pass);
			stat = conn.createStatement();
			String command = "SELECT nickName,password FROM users";
			result = stat.executeQuery(command);
			boolean ok = true;
			while(result.next())
			{
				String Nick = result.getString("nickName");
				String Pass = result.getString("password");
				if(Nick.equals(nickName))
				{
					if(Pass.equals(password))
					{
						responce.setResponseCode(ResponseCodes.UserSignedIn);
						responce.setBody("Hello Boris!");
					// ProcessingOfClient.setHttpResponce(e);
					 ok = false;
					 break;
					}
				}
			}
			if(ok)
			{
				responce.setResponseCode(ResponseCodes.BadRequest);
				responce.setBody("Bye Bye Boris!");
			}
		}
		catch(SQLException ex)
		{
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
