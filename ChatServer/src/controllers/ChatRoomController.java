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


import our.Response;
import our.ResponseCodes;
import our.UserRequest;
import parse.MyXML;
import sender.SenderThread;
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
	public synchronized void deleteUserFromChatRoom(UserRequest http_request,Response http_response)// +
	{
		//(DELETE /chatroom/)[\\d]{1,6}(/member/)[\\d]{1,6}(/)
		//(GET /chatroom/)[\\d]{1,6}(/messages/ HTTP/1.1)
		//DELETE /chatroom/1/member/3/
		
		try
		{
			Connection conn = ConnectToDB.getConnection();
			//////////////////
			RecipientTable table = new RecipientTable(conn);
			
			int start_room = http_request.getRequestString().indexOf("room/")+5;
			int end_room = http_request.getRequestString().indexOf("/mem");
			
			int start_user = http_request.getRequestString().indexOf("er/")+3;
			int end_user = http_request.getRequestString().indexOf("/ HTTP");
			
			String user = http_request.getRequestString().substring(start_user, end_user);
			String chatroom = http_request.getRequestString().substring(start_room, end_room);
			
			
			table.getOverFromRoom(Integer.parseInt(user), Integer.parseInt(chatroom));
			
		
			
		
		
		
			//answer = MyXML.createXML("Chatrooms", "chatroom",set);
		
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
			ChatRoomTable room = new ChatRoomTable(conn);
			//Получил всех юзеров в комнате для отправки им
			ResultSet usersInRoom = room.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			
			//ArrayList<Integer> idUsers = new ArrayList<Integer>();
			
			//Сформировал XML с телом ивента
			String xml = MyXML.createXML_Event_newUserInRoom(usersInRoom);
			
			while(usersInRoom.next())
			{
				//Отсылаю в блядопоток новый ивент куда передаю Response с кодом и телом и юзера адресата
				//if(usersInRoom.getInt("users.id") != Integer.parseInt(map.get("users_id")))
				
					//System.out.println("usersInRoom   "+usersInRoom.getInt("users.id"));
					//System.out.println("online   "+online.get(i));
					//if(usersInRoom.getInt("users.id") == Integer.parseInt(online.get(i).getId()))
					//{
					if(usersInRoom.getInt("users.id") != Integer.parseInt(map.get("users_id")))
						SenderThread.addEvent(new Response(ResponseCodes.newUserInRoom,xml),usersInRoom.getInt("users.id"));
						//break;
					//}
				
					//SenderThread.addEvent(new Response(ResponseCodes.newMsgInRoom,xml),usersInRoom.getInt("users.id"));
			}
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
			
			//ChatRoomTable room = new ChatRoomTable(conn);
			//Получил всех юзеров в комнате для отправки им
			//ResultSet usersInRoom = room.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			
			//ArrayList<Integer> idUsers = new ArrayList<Integer>();
			
			//Сформировал XML с телом ивента
			String xml = MyXML.createXML_Event_newRoom(i, map.get("name"));
			
			RecipientTable rec = new RecipientTable(conn);
			//ArrayList<SocketMap> online = SenderThread.getSocketMap();
			
			//Циклом прогоняюсь по всем юзерам в комнате для отправки
			ResultSet res = rec.select();
			while(res.next())
			{
				//Отсылаю в блядопоток новый ивент куда передаю Response с кодом и телом и юзера адресата
				//if(usersInRoom.getInt("users.id") != Integer.parseInt(map.get("users_id")))
				
					//System.out.println("usersInRoom   "+usersInRoom.getInt("users.id"));
					//System.out.println("online   "+online.get(i));
					//if(usersInRoom.getInt("users.id") == Integer.parseInt(online.get(i).getId()))
					//{
					//if(usersInRoom.getInt("users.id") != Integer.parseInt(map.get("users_id")))
						SenderThread.addEvent(new Response(ResponseCodes.newRoom,xml),res.getInt("users_id"));
						//break;
					//}
				
					//SenderThread.addEvent(new Response(ResponseCodes.newMsgInRoom,xml),usersInRoom.getInt("users.id"));
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