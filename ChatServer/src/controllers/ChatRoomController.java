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

import logger.Logger;

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
import table.UserTable;

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
			Logger.writeEvent("User " + user + " out of the room " + chatroom);
			///////////////////////////////////////////////////////////////////
//			ChatRoomTable room = new ChatRoomTable(conn);
//			//Получил всех юзеров в комнате для отправки им
//			ResultSet usersInRoom = room.getAllUsersFromChatRoom(Integer.parseInt(chatroom));
//			//Сформировал XML с телом ивента
//			String xml = MyXML.createXML_Event_newUserInRoom(usersInRoom);
//			while(usersInRoom.next())
//			{
//				if(usersInRoom.getInt("users.id") != Integer.parseInt(user))
//					SenderThread.addEvent(new Response(ResponseCodes.newUserInRoom,xml),usersInRoom.getInt("users.id"));
//			}
			//////////////////////////////////////////////////
			ChatRoomTable testTable = new ChatRoomTable(conn);
			ResultSet testResult = testTable.getAllUsersFromChatRoom(Integer.parseInt(chatroom));
			UserTable ololo = new UserTable(conn);
			
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
			//////////////////////////////////////////////////
			//Logger.writeEvent("User " + user + " out of the room " + chatroom);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
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
			
			
			
			ChatRoomTable testTable = new ChatRoomTable(conn);
			ResultSet testResult = testTable.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			UserTable ololo = new UserTable(conn);
			
			
			
			
			RecipientTable table = new RecipientTable(conn);
			Recipient userInRoom = new Recipient(map);
			
			
			table.insert(userInRoom.toMap());
			ChatRoomTable table1 = new ChatRoomTable(conn);
			ResultSet users = table1.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			MessagesTable msg = new MessagesTable(conn);
			ResultSet msgs = msg.getMessageFromChatRoom(Integer.parseInt(map.get("chatroom_id")), 1);
			http_response.setBody(MyXML.createXML_Room_UsersMSG(users, msgs));
			http_response.setResponseCode(ResponseCodes.UserAddedToRoom);
			//ChatRoomTable room = new ChatRoomTable(conn);
			//Получил всех юзеров в комнате для отправки им
			System.out.println("Получаю типов с комнаты  " + map.get("chatroom_id"));
			//ResultSet usersInRoom = table1.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			//Сформировал XML с телом ивента
			//String xml = MyXML.createXML_Event_newUserInRoom(testResult);
			System.out.println("======================= START ======================");
			while(testResult.next())
			{
				System.out.println("usersInRoom.next()");
				System.out.println(testResult.getInt("users.id"));
				System.out.println(Integer.parseInt(map.get("users_id")));
				if(testResult.getInt("users.id") != Integer.parseInt(map.get("users_id")))
				{
					String xmlka = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><newUserInRoom><id>"+map.get("users_id")+"</id><nick>"+ololo.returnNick(map.get("users_id"))+"</nick></newUserInRoom>";
					System.out.println("Послал newUserInRoom на " + testResult.getInt("users.id"));
					SenderThread.addEvent(new Response(ResponseCodes.newUserInRoom,xmlka),testResult.getInt("users.id"));
				}
			}
			Logger.writeEvent("User " + userInRoom.getUsersId() + " entered the room " + userInRoom.getChatroomId());
			System.out.println("======================= OVER ======================");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
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
			//Сформировал XML с телом ивента
			String xml = MyXML.createXML_Event_newRoom(i, map.get("name"));
			RecipientTable rec = new RecipientTable(conn);
			//Циклом прогоняюсь по всем юзерам в комнате для отправки
			ResultSet res = rec.select();
			while(res.next())
			{
				SenderThread.addEvent(new Response(ResponseCodes.newRoom,xml),res.getInt("users_id"));
			}
			Logger.writeEvent("Create new room " + i + " user " + chatRoom.getUsersId());
			System.out.println(i);
		} 
		catch (Exception e) 
		{
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
			e.printStackTrace();
		}
	}
	
}