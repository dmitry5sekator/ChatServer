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
	public ApplicationController(){}
	public static void dispatch(UserRequest http_request,Response http_response) 																							
	{
		try
		{
			for(Route r : routes)
			{
				if(r.match(http_request))
				{
					System.out.println(r.getClassName() +" "+ r.getMethod());
					try 
					{
						 Object h = Class.forName(r.getClassName()).newInstance(); 
					     Object[] input={http_request,http_response};
					     Class cl=Class.forName(r.getClassName());
					     Class[] par=new Class[2];
					     par[0]=UserRequest.class;
					     par[1]=Response.class;
					     Method mthd=cl.getMethod(r.getMethod(),par);
					     mthd.setAccessible(true);
					     mthd.invoke(h,input);
					 } 
					 catch (Exception e) 
					 {
					      e.printStackTrace();
					 }
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			http_response.setResponseCode(ResponseCodes.BadRequest);
		}
	}
}