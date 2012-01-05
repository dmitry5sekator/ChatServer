package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import classes.Message;


import our.Response;
import our.ResponseCodes;
import our.UserRequest;
import parse.MyXML;
import sender.SenderThread;

import server.ConnectToDB;
import table.ChatRoomTable;
import table.MessagesTable;
import table.MyMap;

public class MessagesController 
{
	public synchronized void sendMessageToChatRoom(UserRequest http_request,Response http_response)//POST
	{
		try 
		{
		Connection conn = ConnectToDB.getConnection();
		//////////////////
		String body = http_request.getBody();
		HashMap <String,String> map = new HashMap<String,String>();
		
			MyXML.parse(map, body);
			MessagesTable table = new MessagesTable(conn);
			String answer = "";
			MyMap toDB = new MyMap();
			toDB.add("message", map.get("message"));
			toDB.add("users_id", map.get("users_id"));
			toDB.add("chatroom_id", map.get("chatroom_id"));
			//////////////// DTO //////////////////////////
			//Message msg = new Message();
			//msg.setMessage(map.get("message"));
			//msg.setUsersId(Integer.parseInt(map.get("message")));
			//msg.setChatroomId(Integer.parseInt(map.get("chatroom_id")));
			///////////////////////////////////////////////
			////////////// time //////////////////////////
			 
			    Calendar cal = Calendar.getInstance();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
			    String time =  sdf.format(cal.getTime());

			  
			/////////////////////////////////////////////
			toDB.add("time", time);

			MessagesTable r = new MessagesTable(conn);
			table.insert(toDB);
			
			//answer = MyXML.createXML("Chatrooms", "chatroom",set);
			
			http_response.setBody(answer);
			http_response.setResponseCode(ResponseCodes.MessageIsDelivered);
			
			//getAllUsersInRoom arrayINT
			
			
			
			//SenderThread.addEvent(new Response(ResponseCodes.newMsgInRoom,"WE CODE HARD"),0);
			
			
			
			
			
			ChatRoomTable room = new ChatRoomTable(conn);
			//Получил всех юзеров в комнате для отправки им
			ResultSet usersInRoom = room.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			
			//ArrayList<Integer> idUsers = new ArrayList<Integer>();
			
			//Сформировал XML с телом ивента
			String xml = MyXML.createXML_Event_newMsgInRoom(Integer.parseInt(map.get("chatroom_id")), Integer.parseInt(map.get("users_id")), map.get("message"));
			
			
			//ArrayList<SocketMap> online = SenderThread.getSocketMap();
			
			//Циклом прогоняюсь по всем юзерам в комнате для отправки
			
			while(usersInRoom.next())
			{
				//Отсылаю в блядопоток новый ивент куда передаю Response с кодом и телом и юзера адресата
				//if(usersInRoom.getInt("users.id") != Integer.parseInt(map.get("users_id")))
				
					System.out.println("usersInRoom   "+usersInRoom.getInt("users.id"));
					//System.out.println("online   "+online.get(i));
					//if(usersInRoom.getInt("users.id") == Integer.parseInt(online.get(i).getId()))
					//{
					if(usersInRoom.getInt("users.id") != Integer.parseInt(map.get("users_id")))
						SenderThread.addEvent(new Response(ResponseCodes.newMsgInRoom,xml),usersInRoom.getInt("users.id"));
						//break;
					//}
				
					//SenderThread.addEvent(new Response(ResponseCodes.newMsgInRoom,xml),usersInRoom.getInt("users.id"));
			}
			
			
			
			
			
			
			
			//createEvent
			//sendEventToSender
			
			//System.out.println(i);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized void getMessageFromChatRoom(UserRequest http_request,Response http_response) 
	{
		try
		{
		Connection conn = ConnectToDB.getConnection();
		//////////////////
		MessagesTable table = new MessagesTable(conn);
		int start = http_request.getRequestString().lastIndexOf("room/")+5;
		int end = http_request.getRequestString().indexOf("/mess");
		String str = http_request.getRequestString().substring(start, end);
		ResultSet msgs = table.getMessageFromChatRoom(Integer.parseInt(str), 1);
		
		String answer = MyXML.createXML_Room_MSG(msgs);
		
		
		
		//answer = MyXML.createXML("Chatrooms", "chatroom",set);
		
		http_response.setBody(answer);
		http_response.setResponseCode(ResponseCodes.MessageIsDelivered);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
