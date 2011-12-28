package server;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controllers.ApplicationController;

import our.Response;
import our.ResponseCodes;
import our.UserRequest;
import sender.SenderThread;



public class ProcessingOfClient implements Runnable{
	Pattern p = Pattern.compile("(GET /listen/)[\\d]{1,6}(/ HTTP/1.1)");
	private Socket incoming;
	 String img = "<img src=\"http://images2.wikia.nocookie.net/__cb20111028025226/creepypasta/images/c/c5/Troll_face.jpg\">";
	public ProcessingOfClient(Socket incoming)
	{
		this.incoming = incoming;
	}
	/////////////////////////////////
	
	//////////////////////////////////
	@Override
	synchronized public void run() 
	{
		// TODO Auto-generated method stub
		try
		{
				boolean check = false;
				OutputStream output = incoming.getOutputStream();
				InputStream input = incoming.getInputStream();
				PrintWriter r = new PrintWriter(output,true);
				OutputStreamWriter r1 = new OutputStreamWriter(output);
				
				Response httpResponse = null;
				UserRequest httpRequest = null;
				String str = "";
				String str_first = "";
				
				Scanner inn = new Scanner(input);
				
				
				
				str_first = inn.nextLine();
				
				
				
				Matcher m = p.matcher(str_first);
				System.out.println(m.matches());
				if(m.matches())
				{
					//SenderThread.addUserToOnLine(new SocketMap(incoming,str_first.substring(str_first.indexOf("ten/")+4, str_first.indexOf("/ HTTP"))));
					SenderThread.addUserToOnLine(Integer.parseInt(str_first.substring(str_first.indexOf("ten/")+4, str_first.indexOf("/ HTTP"))), incoming);
				}
				else
				{

					System.out.println("======================================");
					System.out.println(incoming.getPort());
					System.out.println(incoming.getInetAddress().toString());
					System.out.println("======================================");
				
					ArrayList<String> httpParam = new ArrayList<String>();
					try
					{
						String temp;
						while(inn.hasNextLine())
						{
							String template = inn.nextLine();
//							if(template.equals(""))
//							{
//								break;
//							}
							httpParam.add(template);
							str += template;
						}
						
						httpRequest = new UserRequest();
						httpResponse = new Response();
						if(str.lastIndexOf("<?xml version=") != -1)
						{
							httpRequest.setBody(str.substring(str.lastIndexOf("<?xml version=")));
						}
						else
						{
							httpRequest.setBody("");
						}
						httpRequest.setRequestString(str_first);
						httpRequest.setParam(httpParam);
					}
					catch(Exception e)
					{
						
						r1.write("<html><head></head><body><h1>BAD REQUEST!!!</h1>"+img+"<h2>problem???</h2></body></html>");
						r1.flush();
						r1.close();
						incoming.shutdownOutput();
						incoming.close();
						e.printStackTrace();
					}

					ApplicationController.dispatch(httpRequest,httpResponse);

					System.out.println();
					System.out.println(httpResponse.toString());
					System.out.println();
					r.write(httpResponse.toString());
					r.flush();
					r.close();
					incoming.shutdownOutput();
					incoming.close();
					
				}
				
				
				
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

