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

import logger.Logger;

import classes.User;



import our.Response;
import our.ResponseCodes;
import our.UserRequest;
import parse.MyXML;
import sender.SenderThread;
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
				http_response.setBody(answer);
				http_response.setResponseCode(ResponseCodes.UserSignedIn);
				Logger.writeEvent("User is online " + map.get("id"));
			}
			else
			{
				http_response.setBody("");
				http_response.setResponseCode(ResponseCodes.UserNotFound);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
		}
	}
	public void logOut(UserRequest http_request,Response http_response)
	{
		
		try
		{
			Connection conn = ConnectToDB.getConnection();
			///////////
			int start_user = http_request.getRequestString().indexOf("logout/")+7;
			int end_user = http_request.getRequestString().indexOf("/room");
			
			int start_room = http_request.getRequestString().indexOf("/room/")+6;
			int end_room = http_request.getRequestString().indexOf("/ HTTP/1.1");
			
			String user = http_request.getRequestString().substring(start_user, end_user);
			String chatroom = http_request.getRequestString().substring(start_room, end_room);
			//table.getOverFromRoom(Integer.parseInt(user), Integer.parseInt(chatroom));
			//http_response.setBody("");
			//http_response.setResponseCode(ResponseCodes.UserLiveAloneFromTheRoom);
			Logger.writeEvent("User " + user + " offline");
			SenderThread.removeUserFromOnLine(Integer.parseInt(user));
			//////////
//			Connection conn = ConnectToDB.getConnection();
//			String body = http_request.getBody();
//			MyXML.parse(map, body);
//			UserTable table = new UserTable(conn);
//			String answer = "";
//			boolean check = table.checkSingIn(Integer.parseInt(map.get("id")), map.get("password"));
//			if(check)
//			{
//				ChatRoomTable room = new ChatRoomTable(conn);
//				ResultSet set = room.select();
//				answer = MyXML.createXML("Chatrooms", "chatroom",set);
//			}
			http_response.setBody("");
			http_response.setResponseCode(ResponseCodes.UserOffLine);
			Logger.writeEvent("User is offline " + map.get("id"));
			
			
			
			
			ChatRoomTable testTable = new ChatRoomTable(conn);
			ResultSet testResult = testTable.getAllUsersFromChatRoom(Integer.parseInt(chatroom));
			UserTable ololo = new UserTable(conn);
			
			if(!chatroom.equals("777"))
			{
			System.out.println("======================= START ======================");
			while(testResult.next())
			{
				System.out.println("usersInRoom.next()");
				System.out.println(testResult.getInt("users.id"));
				System.out.println(Integer.parseInt(user));
				if(testResult.getInt("users.id") != Integer.parseInt(user))
				{
					String xmlka = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><UserLiveAloneFromTheRoom><id>"+user+"</id><nick>"+ololo.returnNick(user)+"</nick></UserLiveAloneFromTheRoom>";
					System.out.println("Послал newUserInRoom на " + testResult.getInt("users.id"));
					SenderThread.addEvent(new Response(ResponseCodes.UserLiveAloneFromTheRoom,xmlka),testResult.getInt("users.id"));
				}
			}
			Logger.writeEvent("User " +user + " alone from the room " + chatroom);
			System.out.println("======================= OVER ======================");
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
		}
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
			Logger.writeEvent("Create new user " + i);
			System.out.println(i);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
		}
	}
	
}