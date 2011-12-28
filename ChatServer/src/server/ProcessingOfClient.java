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

import controllers.ApplicationController;

import our.Response;
import our.ResponseCodes;
import our.UserRequest;


public class ProcessingOfClient implements Runnable{
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
				OutputStream output = incoming.getOutputStream();
				InputStream input = incoming.getInputStream();
				PrintWriter r = new PrintWriter(output,true);
				OutputStreamWriter r1 = new OutputStreamWriter(output);
				///
				Response httpResponse = null;
				UserRequest httpRequest = null;
				String str = "";
				String str_first = "";
				Scanner inn = new Scanner(input);
				str_first = inn.nextLine();
				System.out.println("======================================");
				System.out.println(incoming.getLocalPort());
				System.out.println(incoming.getPort());
				System.out.println(incoming.getInetAddress().toString());
				System.out.println("======================================");
				//input.close();
				//incoming.shutdownInput();
				ArrayList<String> httpParam = new ArrayList<String>();
				try
				{
					String temp;
					while(inn.hasNextLine())
					{
						String template = inn.nextLine();
						//if(template.equals(""))
						//{
							//break;
						//}
						httpParam.add(template);
						str += template;
					}
					
					httpRequest = new UserRequest();
					httpResponse = new Response();
					httpRequest.setBody(str.substring(str.lastIndexOf("<?xml version=")));
					httpRequest.setRequestString(str_first);
					httpRequest.setParam(httpParam);
				}
				catch(Exception e)
				{
					//r.print(httpResponse.toString().getBytes());
					r1.write("<html><head></head><body><h1>BAD REQUEST!!!</h1>"+img+"<h2>problem???</h2></body></html>");
					r1.flush();
					r1.close();
					incoming.shutdownOutput();
					incoming.close();
					e.printStackTrace();
				}
				//if(AuthChecker.checkAuth(httpRequest.AuthLogin(),httpRequest.AuthPassword()))
				//{
					ApplicationController.dispatch(httpRequest,httpResponse);
					//r.write(httpResponse.getBody());
				//}
				//else if(httpRequest.getRequestString().substring(0, 12) == "POST /users/")
				//{
					//ApplicationController.dispatch(httpRequest,httpResponse);
					//r.write(httpResponse.getBody());
				//}
				
				//r.print(httpResponse.toString().getBytes());
					r.write(httpResponse.toString());
					//r.write("<html><head></head><body><h1>BAD REQUEST!!!</h1>"+img+"<h2>problem???</h2></body></html>");
					r.flush();
					r.close();
				incoming.shutdownOutput();
				incoming.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

