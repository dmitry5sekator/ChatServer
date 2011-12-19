package server;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
import our.UserRequest;


public class ProcessingOfClient implements Runnable{
	private Socket incoming;
	 
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
				OutputStreamWriter r = new OutputStreamWriter(output);
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
				try
				{
					String temp;
					while(inn.hasNextLine())
					{
						
						str += inn.nextLine();
					}
					
					httpRequest = new UserRequest();
					httpResponse = new Response();
					httpRequest.setBody(str.substring(str.lastIndexOf("<?xml version=")));
					httpRequest.setRequestString(str_first);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				 ApplicationController.dispatch(httpRequest,httpResponse);
				//System.out.println(httpResponce.getResponseCode());
				 r.write(httpResponse.getBody());
				incoming.shutdownOutput();
				incoming.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

