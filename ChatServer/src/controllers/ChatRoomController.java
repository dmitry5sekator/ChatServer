package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import classes.ChatRoom;
import classes.Recipient;


import our.Response;
import our.ResponseCodes;
import our.UserRequest;
import parse.MyXML;
import sender.SenderThread;
import server.ConnectToDB;
import table.ChatRoomTable;
import table.MessagesTable;
import table.RecipientTable;

public class ChatRoomController
{
	public void deleteAllUsersFromChatRoom(UserRequest http_request,Response http_response) //+
	{
		
	}
	public synchronized void deleteUserFromChatRoom(UserRequest http_request,Response http_response)// +
	{
		try
		{
			Connection conn = ConnectToDB.getConnection();
			RecipientTable table = new RecipientTable(conn);
			int start_room = http_request.getRequestString().indexOf("room/")+5;
			int end_room = http_request.getRequestString().indexOf("/mem");
			int start_user = http_request.getRequestString().indexOf("er/")+3;
			int end_user = http_request.getRequestString().indexOf("/ HTTP");
			String user = http_request.getRequestString().substring(start_user, end_user);
			String chatroom = http_request.getRequestString().substring(start_room, end_room);
			table.getOverFromRoom(Integer.parseInt(user), Integer.parseInt(chatroom));
			http_response.setBody("");
			http_response.setResponseCode(ResponseCodes.UserLiveAloneFromTheRoom);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			Recipient userInRoom = new Recipient(map);
			table.insert(userInRoom.toMap());
			ChatRoomTable table1 = new ChatRoomTable(conn);
			ResultSet users = table1.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			MessagesTable msg = new MessagesTable(conn);
			ResultSet msgs = msg.getMessageFromChatRoom(Integer.parseInt(map.get("chatroom_id")), 1);
			http_response.setBody(MyXML.createXML_Room_UsersMSG(users, msgs));
			http_response.setResponseCode(ResponseCodes.UserAddedToRoom);
			ChatRoomTable room = new ChatRoomTable(conn);
			//ѕолучил всех юзеров в комнате дл€ отправки им
			ResultSet usersInRoom = room.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			//—формировал XML с телом ивента
			String xml = MyXML.createXML_Event_newUserInRoom(usersInRoom);
			while(usersInRoom.next())
			{
				if(usersInRoom.getInt("users.id") != Integer.parseInt(map.get("users_id")))
					SenderThread.addEvent(new Response(ResponseCodes.newUserInRoom,xml),usersInRoom.getInt("users.id"));
			}
		} 
		catch (Exception e) 
		{
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
			ChatRoom chatRoom = new ChatRoom(map);
			table.insert(chatRoom.toMap());
			int i = table.returnId(map.get("name"), map.get("users_id"));
			http_response.setBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><chatroom><id>"+i+"</id></chatroom>");
			http_response.setResponseCode(ResponseCodes.RoomAdded);
			//—формировал XML с телом ивента
			String xml = MyXML.createXML_Event_newRoom(i, map.get("name"));
			RecipientTable rec = new RecipientTable(conn);
			//÷иклом прогон€юсь по всем юзерам в комнате дл€ отправки
			ResultSet res = rec.select();
			while(res.next())
			{
				SenderThread.addEvent(new Response(ResponseCodes.newRoom,xml),res.getInt("users_id"));
			}
			System.out.println(i);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}