package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;



import our.Response;
import our.ResponseCodes;
import our.UserRequest;
import parse.MyXML;
import server.ConnectToDB;
import table.ChatRoomTable;
import table.MyMap;
import table.UserTable;


public class UserController 
{
	private HashMap <String,String> map = new HashMap <String,String>();
	public void getUser(UserRequest http_request,Response http_response) // +
	{
		
	}
	public void signIn(UserRequest http_request,Response http_response) //POST + 
	{
		try
		{
		/////////////////
		Connection conn = ConnectToDB.getConnection();
		//////////////////
		String body = http_request.getBody();
		HashMap <String,String> map = new HashMap<String,String>();
		
			MyXML.parse(map, body);
			UserTable table = new UserTable(conn);
			String answer = "";
//			MyMap toDB = new MyMap();
//			toDB.add("nick", map.get("nick"));
//			toDB.add("password", map.get("password"));
//			toDB.add("info", map.get("info"));
			boolean check = table.checkSingIn(Integer.parseInt(map.get("id")), map.get("password"));
			if(check)
			{
				ChatRoomTable room = new ChatRoomTable(conn);
				ResultSet set = room.select();
				answer = MyXML.createXML("Chatrooms", "chatroom",set);
			}
			
			http_response.setBody(answer);
			http_response.setResponseCode(ResponseCodes.UserSignedIn);
			
			
			
			//System.out.println(i);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void logOut(UserRequest http_request,Response http_response)
	{
		
	}
	public void updateUser(UserRequest http_request,Response http_response) // +
	{
		
	}
	public void createUser(UserRequest http_request,Response http_response)//POST + 
	{
		try 
		{
		/////////////////
		Connection conn = ConnectToDB.getConnection();
		//////////////////
		String body = http_request.getBody();
		HashMap <String,String> map = new HashMap<String,String>();
		
			MyXML.parse(map, body);
			UserTable table = new UserTable(conn);
			MyMap toDB = new MyMap();
			toDB.add("nick", map.get("nick"));
			toDB.add("password", map.get("password"));
			toDB.add("info", map.get("info"));
			table.insert(toDB);
			int i = table.returnId(map.get("nick"), map.get("password"));
			
			http_response.setBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><user><id>"+i+"</id></user>");
			http_response.setResponseCode(ResponseCodes.UserAdded);
			
			
			
			System.out.println(i);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}