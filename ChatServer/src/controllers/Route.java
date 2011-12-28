package controllers;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import our.UserRequest;



public class Route {
	private String pattern;
	private String classname;
	private String method;
	public Route(String pattern,String classname,String method)
	{
		this.pattern = pattern;
		this.classname = classname;
		this.method = method;
	}
	public boolean match(UserRequest httpRequest)
	{
//		System.out.println(pattern+" "+httpRequest.getRequestString());
//		Pattern p = Pattern.compile(pattern);
//		Matcher m = p.matcher("GET /chatroom/1/messages/ HTTP/1.1");
//		System.out.println(m.matches());
		//Pattern p = Pattern.compile(pattern);
//		System.out.println(httpRequest.getRequestString());
//		System.out.println(pattern.toString());
//		Matcher m = pattern.matcher(httpRequest.getRequestString());
//		System.out.println(m.matches());
//		
//			//System.out.println(httpRequest.getRequestString().substring(0, httpRequest.getRequestString().indexOf(" HTTP")));
//		System.out.println();
//			System.out.println(pattern.toString());
//			System.out.println(httpRequest.getRequestString());
//			System.out.println(matcher.matches());
//			System.out.println();
//			
		
//		System.out.println(pattern);
//		System.out.println(httpRequest.getRequestString());
//		String pattern1 = pattern;
//		///
//		Pattern p = Pattern.compile(pattern1);
//		Matcher m = p.matcher(httpRequest.getRequestString());
//			return m.matches();
		////////////////////////////////////////////////////////
		
		// U MNU ISTERIKA
		System.out.println("PATTERN SUKA :::: "  + pattern);
		
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(httpRequest.getRequestString());
		return m.matches();
	}
	public String getClassName()
	{
		return classname;
	}
	public String getMethod()
	{
		return method;
	}
	
/*	
	private volatile static Route instance;
	public ArrayList <String> method = new ArrayList<String>();
	public ArrayList <String> pattern = new ArrayList<String>();
	public ArrayList <String> controllerClass = new ArrayList<String>();
	public ArrayList <String> controllerMethod = new ArrayList<String>();
	private int [] space = new int[3];
	public int ind = 0;
	private Route()
	{
		
	}
	public static Route getInstance() {
        if (instance == null) {
            synchronized (Route.class) {
                if (instance == null) {
                    instance = new Route();
                    try
        			{
        				File file = new File("route_db.rdb");
        				Scanner in = new Scanner(file);
        				while(in.hasNextLine())
        					instance.uploadRoutes(in.nextLine());
        				instance.showRoutes();
        			}
        			catch(IOException e)
        			{
        				e.printStackTrace();
        			}
                }
            }
        }
        return instance;
    }
	private void uploadRoutes(String line)
	{
		uploadRoute(line);
	}
	private void uploadRoute(String line)
	{
		getSpaces(line);
		this.method.add(line.substring(0, space[0]));
		this.pattern.add(line.substring(space[0]+1, space[1]));
		this.controllerClass.add(line.substring(space[1]+1, space[2]));
		this.controllerMethod.add(line.substring(space[2]+1, line.length()));
	}
	private void getSpaces(String line)
	{
		space[0] = line.indexOf(" ");
		space[1] = line.indexOf(" ",space[0]+1);
		space[2] = line.indexOf(" ", space[1]+1);
	}
	public void showRoutes()
	{
		for(int i = 0;i < method.size();i++)
		{
			System.out.println(method.get(i) + " " + pattern.get(i) + " " + controllerClass.get(i) + " " + controllerMethod.get(i));
		}
	}
	public boolean checkAndExecute(String method,String pattern)
	{
		for(int i = 0;i < this.method.size();i++)
		{
			System.out.println((this.method.get(i).equals(method)) + "\t" + (this.pattern.get(i) == pattern));
			if(	(this.method.get(i).equals(method)) && (this.pattern.get(i).equals(pattern))	)
			{
				ind = i;
				return true;
			}
		}
		ind = 0;
		return false;
	}
	*/
}