package sender;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import our.Response;

public class SenderThread implements Runnable
{
	static Queue<Response> events = new LinkedList<Response>();
	//static ArrayList<SocketMap> online_list =new ArrayList<SocketMap>();
	static ArrayList <SenderMap> online_list = new ArrayList<SenderMap>();
	//            idTable,idOnLine,Socket
	//              +                 +
	@Override
	public synchronized void run() 
	{
		Iterator it=events.iterator();
		while(true)
		{
			//Прогоняюсь если есть ивенты 
			if(it.hasNext())
			{
				try
				{
					System.out.println("SEND TO = "+events.peek().getMailto());
					int idArray = getIdArray(events.peek().getMailto());
					OutputStream output = online_list.get(idArray).s.getOutputStream();
					PrintWriter r = new PrintWriter(output,true);
					r.println(events.peek().TOSTR());
					events.poll();
				}
				catch(Exception e)
				{e.printStackTrace();}
			}
			try 
			{
				Thread.currentThread().sleep(50);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private synchronized int getIdArray(int TableId)
	{
		for(int i = 0;i < online_list.size();i++)
		{
			if(online_list.get(i).idTable == TableId)
			{
				return online_list.get(i).idArray;
			}
		}
		return 0;
	}
	public synchronized static void addEvent(Response httpEvent,int mailto)
	{
		httpEvent.setMailto(mailto);
		System.out.println(httpEvent.getMailto());
		events.add(httpEvent);
	}
	public synchronized static void addUserToOnLine(int id,Socket s) throws IOException
	{
		online_list.add(new SenderMap(id,s));
		online_list.get(online_list.size()-1).setIdArray(online_list.size()-1);
		OutputStream output = s.getOutputStream();
		PrintWriter r = new PrintWriter(output,true);
		System.out.println("Успешно добавлен слушатель! ip : " + s.getInetAddress().toString() + " port : " + s.getPort());
	}

}