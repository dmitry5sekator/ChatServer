package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import our.UserRequest;
import parse.XMLParser;


public class UserController 
{
	HashMap <String,String> map = new HashMap <String,String>();
	public void getUser(UserRequest http_request)
	{
		
		
	}
	public void test()
	{
		System.out.println("Hi");
	}
	public void getAllUsers(UserRequest http_request)
	{
		
	}
	public void signIn(UserRequest http_request) throws IOException //POST
	{
		Random r = new Random();
		String way = "request_files/signIn" + Integer.toString(r.nextInt()) + ".req";
		File f = new File(way);
		f.createNewFile();
		FileWriter write = new FileWriter(new File(way));
		write.write(http_request.getBody());
		write.close();
		XMLParser.parse(way, map);
		String name = map.get("nickName");
		String password = map.get("password");
		//ConnectToDB.signIn(name, password,httpResponce);
		
	}
	public void logOut(UserRequest http_request)
	{
		
	}
	public void updateUser(UserRequest http_request)
	{
		
	}
	public void createUser(UserRequest http_request) //POST
	{
		
	}
	
}