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
					System.out.println("ВНИМАНИе =  "+events.peek().getMailto());
					int idArray = getIdArray(events.peek().getMailto());
					OutputStream output = online_list.get(idArray).s.getOutputStream();
					
//						if(Integer.parseInt(online_list.get(i).getId()) == events.peek().getMailto())
//						{
//							output = online_list.get(i).getSocket().getOutputStream();
//							break;
//						}
						//output = online_list.get(i).getSocket().getOutputStream();
						
					
					
					//OutputStream output = user.getSocket().getOutputStream();
					PrintWriter r = new PrintWriter(output,true);
					r.println(events.peek().TOSTR());
				r.println("to String = kall");
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
//					System.out.print("====================");
//					System.out.print("ОЛООЛ ПАНИКО отослал ивент " + events.peek().getResponseCode() + " НА : "  + events.peek().getMailto());
//					System.out.print("====================");
					events.poll();
				}
				catch(Exception e)
				{e.printStackTrace();}
			}
			try 
			{
				Thread.currentThread().sleep(200);
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
//		System.out.print("====================");
//		System.out.print("ОЛООЛ ПАНИКО отсылаю ивент " + httpEvent.getResponseCode() + " НА : "  + httpEvent.getMailto());
//		System.out.print("====================");
		httpEvent.setMailto(mailto);
		System.out.println(httpEvent.getMailto());
		events.add(httpEvent);
	}
	public synchronized static void addUserToOnLine(int id,Socket s) throws IOException
	{
//		online_list.put(new IDs(id),s);
//		online_list.get(id).;
//		online_list.size();
		//setIdArray
		online_list.add(new SenderMap(id,s));
		online_list.get(online_list.size()-1).setIdArray(online_list.size()-1);
		
		OutputStream output = s.getOutputStream();
		PrintWriter r = new PrintWriter(output,true);
		//r.println("200 OK");
		System.out.println("Успешно добавлен слушатель! ip : " + s.getInetAddress().toString() + " port : " + s.getPort());
	}
//	public int getIdToSend(int id)
//	{
//		return online_list.)
//	}
	
//	public static void addUserToOnLine(SocketMap user) throws IOException
//	{
//		online_list.add(user);
//		
//		OutputStream output = user.getSocket().getOutputStream();
//		PrintWriter r = new PrintWriter(output,true);
//		r.println("200 OK");
//		System.out.println("Успешно добавлен слушатель! ip : " + user.socket.getInetAddress().toString() + " port : " + user.socket.getPort());
//	}
//	public static ArrayList<SocketMap> getSocketMap()
//	{
//		return online_list;
//		
//	}
//	public static ArrayList<Integer> getOnLineList()
//	{
//		ArrayList<Integer> users =new ArrayList<Integer>();
//		for(int i = 0;i < online_list.size();i++)
//		{
//			users.add(Integer.parseInt(online_list.get(i).getId()));
//		}
//		return users;
//	}
}