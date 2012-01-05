package server;

import sender.SenderThread;
import injection.AuthCheckerReader;
import injection.FileReader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import logger.Logger;

import sender.SenderThread;

import controllers.ApplicationController;



public class Engine {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		ConnectToDB.startUpConnection();
		
		ApplicationController ap = new ApplicationController();
		
		AuthChecker checker = new AuthChecker();
		AuthCheckerReader checkerReader = new AuthCheckerReader();
		checkerReader.read(checker);
		
		FileReader fr = new FileReader("route_db.rdb");
		fr.read(ap);
		
		try
		{
			SenderThread sender = new SenderThread();
			Thread th = new Thread(sender);
			th.start();
			
			ServerSocket s = new ServerSocket(80);
			System.out.println("Server is ON");
			while(true)
			{
				Socket incoming = s.accept();
				Runnable r = new ProcessingOfClient(incoming);
				Thread t = new Thread(r);
				t.start();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
		}
	}

}
