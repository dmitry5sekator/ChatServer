package controllers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import our.Response;
import our.ResponseCodes;
import our.UserRequest;




public class ApplicationController 
{
	private static ArrayList <Route> routes = new ArrayList<Route>();
	public void addRoute(Pattern pattern,String className,String method)
	{
		routes.add(new Route(pattern,className,method));
	}
	//public ApplicationController appController = new ApplicationController();
	public ApplicationController(){}
	public static void dispatch(UserRequest http_request,Response httpResponce) 																							
	{
		/////////////////////////////////////////////////////////
		try
		{
			for(Route r : routes)
			{
				if(r.match(http_request))
				{
//					System.out.println(r.getClassName() +" "+ r.getMethod());
//					//Object h = Class.forName(r.getClassName()).newInstance(); 
//					Object h = Class.forName(r.getClassName());
//					Class obj = h.getClass();   
//					
//					Class[] paramTypes = new Class[] { UserRequest.class, Response.class };
//					Method m = obj.getMethod(r.getMethod(), paramTypes); 
//				
//					
//					
//					
//					System.out.println(m); 
//					Object[] args = new Object[] { http_request, httpResponce };
//					if(m != null) 
//					{//вызываем 
//						m.invoke(h ,args); 
//					} 
//					h = null; 
//					obj = null; 
//					m = null; 
					//////////////////////////
//					String this_class,this_method;
//					this_class = r.getClassName();
//					this_method = r.getMethod();
//					Object h = Class.forName(this_class).newInstance(); 
//					Class obj = h.getClass();                               
//					Method m; 
//					
//						m = obj.getMethod(this_method, UserRequest.class);
//						m.setAccessible(true); 
//						System.out.println(m); 
//						if(m != null) 
//						{//âûçûâàåì 
//							m.invoke(h ,http_request); 
//						} 
//						h = null; 
//						obj = null; 
//						m = null; 
					 
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			httpResponce.setResponseCode(ResponseCodes.BadRequest);
		}
	}
}