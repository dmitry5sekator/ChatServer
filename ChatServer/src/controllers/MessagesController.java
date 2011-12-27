package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;


import our.Response;
import our.ResponseCodes;
import our.UserRequest;
import parse.MyXML;
import table.MessagesTable;
import table.MyMap;

public class MessagesController 
{
	public void sendMessageToChatRoom(UserRequest http_request,Response http_response) throws ParserConfigurationException, TransformerFactoryConfigurationError, IOException, TransformerException, SQLException //POST
	{
		final String URL = "jdbc:mysql://localhost/chat_db";
		
		final String USERNAME = "root";
		
		final String PASSWORD = "ghbvf777ghbvf";
		
		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		//////////////////
		String body = http_request.getBody();
		HashMap <String,String> map = new HashMap<String,String>();
		try 
		{
			MyXML.parse(map, body);
			MessagesTable table = new MessagesTable(conn);
			String answer = "";
			MyMap toDB = new MyMap();
			toDB.add("message", map.get("message"));
			toDB.add("users_id", map.get("users_id"));
			toDB.add("chatroom_id", map.get("chatroom_id"));
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
			
			
			
			//System.out.println(i);
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getMessageFromChatRoom(UserRequest http_request,Response http_response) throws SQLException, ParserConfigurationException, TransformerFactoryConfigurationError, IOException, TransformerException
	{
		final String URL = "jdbc:mysql://localhost/chat_db";
		
		final String USERNAME = "root";
		
		final String PASSWORD = "ghbvf777ghbvf";
		
		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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
}
