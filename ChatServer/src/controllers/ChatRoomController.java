package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;


import our.Response;
import our.ResponseCodes;
import our.UserRequest;
import parse.MyXML;
import server.ConnectToDB;
import table.ChatRoomTable;
import table.MessagesTable;
import table.MyMap;
import table.RecipientTable;

public class ChatRoomController
{
	public void deleteAllUsersFromChatRoom(UserRequest http_request,Response http_response) //+
	{
		
	}
	public void deleteUserFromChatRoom(UserRequest http_request,Response http_response)// +
	{
		
	}
	public void deleteChatRoom(UserRequest http_request,Response http_response) //+
	{
		
	}
	public void addToChatRoom(UserRequest http_request,Response http_response) //POST +
	{
		try 
		{
		Connection conn = ConnectToDB.getConnection();
		String body = http_request.getBody();
		HashMap <String,String> map = new HashMap<String,String>();
		
		
		
			MyXML.parse(map, body);
			RecipientTable table = new RecipientTable(conn);
			MyMap toDB = new MyMap();
			toDB.add("users_id", map.get("users_id"));
			toDB.add("chatroom_id", map.get("chatroom_id"));
			table.insert(toDB);
			
			ChatRoomTable table1 = new ChatRoomTable(conn);
			
			ResultSet users = table1.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			
			MessagesTable msg = new MessagesTable(conn);
			ResultSet msgs = msg.getMessageFromChatRoom(Integer.parseInt(map.get("chatroom_id")), 1);
			http_response.setBody(MyXML.createXML_Room_UsersMSG(users, msgs));
			//http_response.setBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><chatroom><id>"+i+"</id></chatroom>");
			http_response.setResponseCode(ResponseCodes.UserAddedToRoom);
			
			//System.out.println(MyXML.createXML_Room_UsersMSG(users, msgs));
			
			//System.out.println(i);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void createChatRoom(UserRequest http_request,Response http_response)//POST + 
	{
		try 
		{
		Connection conn = ConnectToDB.getConnection();
		String body = http_request.getBody();
		HashMap <String,String> map = new HashMap<String,String>();
		
		
		
			MyXML.parse(map, body);
			ChatRoomTable table = new ChatRoomTable(conn);
			MyMap toDB = new MyMap();
			toDB.add("name", map.get("name"));
			toDB.add("info", map.get("info"));
			toDB.add("users_id", map.get("users_id"));
			table.insert(toDB);
			int i = table.returnId(map.get("name"), map.get("users_id"));
			
			http_response.setBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><chatroom><id>"+i+"</id></chatroom>");
			http_response.setResponseCode(ResponseCodes.RoomAdded);
			
			//getAllOnLineUsers array INT
			//createEvent
			//sendEventToSender
			
			System.out.println(i);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}