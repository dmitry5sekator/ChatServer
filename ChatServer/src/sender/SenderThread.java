package sender;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import our.Response;

public class SenderThread implements Runnable
{
	static Queue<Response> events = new LinkedList<Response>();
	static ArrayList<SocketMap> online_list =new ArrayList<SocketMap>();
	@Override
	public void run() 
	{
		 Iterator it=events.iterator();
		while(true)
		{
			//Прогоняюсь если есть ивенты 
			if(it.hasNext())
			{
				try
				{
//					Socket s = online_list.get(events.peek().getMailto()).getSocket();
//					//////////
//					
//					OutputStream output = s.getOutputStream();
//					
//					PrintWriter r = new PrintWriter(output,true);
//					
//					r.println(events.peek());
//					
//					///////////
//					//Тут отправлю ивент на клиента
//					System.out.println("Alloha");
//					//и удалю из очереди
					events.poll();
				}
				catch(Exception e)
				{e.printStackTrace();}
			}
			try 
			{
				Thread.currentThread().sleep(1);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void addEvent(Response httpEvent,int mailto)
	{
		httpEvent.setMailto(mailto);
		events.add(httpEvent);
	}
	public static void addUserToOnLine(SocketMap user)
	{
		online_list.add(user);
	}
}