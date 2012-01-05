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

public class MessagesController 
{
	public synchronized void sendMessageToChatRoom(UserRequest http_request,Response http_response)//POST
	{
		try 
		{
			Connection conn = ConnectToDB.getConnection();
			String body = http_request.getBody();
			HashMap <String,String> map = new HashMap<String,String>();
			MyXML.parse(map, body);
			MessagesTable table = new MessagesTable(conn);
			String answer = "";
			Message msg = new Message(map);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
			String time =  sdf.format(cal.getTime());
			msg.setTime(time);
			table.insert(msg.toMap());
			http_response.setBody(answer);
			http_response.setResponseCode(ResponseCodes.MessageIsDelivered);
			ChatRoomTable room = new ChatRoomTable(conn);
			//������� ���� ������ � ������� ��� �������� ��
			ResultSet usersInRoom = room.getAllUsersFromChatRoom(Integer.parseInt(map.get("chatroom_id")));
			//����������� XML � ����� ������
			String xml = MyXML.createXML_Event_newMsgInRoom(Integer.parseInt(map.get("chatroom_id")), Integer.parseInt(map.get("users_id")), map.get("message"));
			//������ ���������� �� ���� ������ � ������� ��� ��������
			while(usersInRoom.next())
			{
				//������� � ���������� ����� ����� ���� ������� Response � ����� � ����� � ����� ��������
				System.out.println("usersInRoom   "+usersInRoom.getInt("users.id"));
				if(usersInRoom.getInt("users.id") != Integer.parseInt(map.get("users_id")))
					SenderThread.addEvent(new Response(ResponseCodes.newMsgInRoom,xml),usersInRoom.getInt("users.id"));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public synchronized void getMessageFromChatRoom(UserRequest http_request,Response http_response) 
	{
		try
		{
			Connection conn = ConnectToDB.getConnection();
			MessagesTable table = new MessagesTable(conn);
			int start = http_request.getRequestString().lastIndexOf("room/")+5;
			int end = http_request.getRequestString().indexOf("/mess");
			String str = http_request.getRequestString().substring(start, end);
			ResultSet msgs = table.getMessageFromChatRoom(Integer.parseInt(str), 1);
			String answer = MyXML.createXML_Room_MSG(msgs);
			http_response.setBody(answer);
			http_response.setResponseCode(ResponseCodes.MessageIsDelivered);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
