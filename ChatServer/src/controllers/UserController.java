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

import classes.User;



import our.Response;
import our.ResponseCodes;
import our.UserRequest;
import parse.MyXML;
import server.ConnectToDB;
import table.ChatRoomTable;
import table.UserTable;


public class UserController 
{
	private User user = new User();
	private HashMap <String,String> map = new HashMap <String,String>();
	public void getUser(UserRequest http_request,Response http_response) // +
	{
		
	}
	public synchronized void signIn(UserRequest http_request,Response http_response) //POST + 
	{
		try
		{
			Connection conn = ConnectToDB.getConnection();
			String body = http_request.getBody();
			MyXML.parse(map, body);
			UserTable table = new UserTable(conn);
			String answer = "";
			boolean check = table.checkSingIn(Integer.parseInt(map.get("id")), map.get("password"));
			if(check)
			{
				ChatRoomTable room = new ChatRoomTable(conn);
				ResultSet set = room.select();
				answer = MyXML.createXML("Chatrooms", "chatroom",set);
			}
			http_response.setBody(answer);
			http_response.setResponseCode(ResponseCodes.UserSignedIn);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public void logOut(UserRequest http_request,Response http_response)
	{
		
	}
	public void updateUser(UserRequest http_request,Response http_response) // +
	{
		
	}
	public synchronized void createUser(UserRequest http_request,Response http_response)//POST + 
	{
		try 
		{
			Connection conn = ConnectToDB.getConnection();
			String body = http_request.getBody();
			MyXML.parse(map, body);
			UserTable table = new UserTable(conn);
			user = new User(map);
			table.insert(user.toMap());
			int i = table.returnId(map.get("nick"), map.get("password"));
			http_response.setBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><user><id>"+i+"</id></user>");
			http_response.setResponseCode(ResponseCodes.UserAdded);
			System.out.println(i);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}