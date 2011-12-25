package server;

import injection.AuthCheckerReader;
import injection.FileReader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import controllers.ApplicationController;



public class Engine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ConnectToDB.test();
		// My First Commit :*
		
		ApplicationController ap = new ApplicationController();
		
		AuthChecker checker = new AuthChecker();
		AuthCheckerReader checkerReader = new AuthCheckerReader();
		checkerReader.read(checker);
		
		FileReader fr = new FileReader("route_db.rdb");
		fr.read(ap);
		
		try
		{
			
			ServerSocket s = new ServerSocket(80);
			System.out.println("Server is ON");
			// Подгружаю данные в Route
			//Route route = Route.getInstance();
//			route.showRoutes();
			
			while(true)
			{
				Socket incoming = s.accept();
				System.out.println("Connect...");
				Runnable r = new ProcessingOfClient(incoming);
				Thread t = new Thread(r);
				t.start();
				
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
