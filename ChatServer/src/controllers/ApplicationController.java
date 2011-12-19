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
	public static void dispatch(UserRequest http_request,Response http_response) 																							
	{
		/////////////////////////////////////////////////////////
		try
		{
			for(Route r : routes)
			{
				if(r.match(http_request))
				{
					http_request.setBody("BODY OF REQUEST");
					http_request.setRequestString("REQUEST STRING FROM REQUEST");
					http_response.setBody("BODY OF RESPONSE");
					http_response.setResponseCode(ResponseCodes.UserUpdated);
					System.out.println(r.getClassName() +" "+ r.getMethod());
					/////////////WORKED CODE/////////////
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
					 //////////////////////////////////////
					//             SOME               //
//					String this_class,this_method;
//					this_class = r.getClassName();
//					this_method = r.getMethod();
//					Object h = Class.forName(this_class).newInstance(); 
//					Class obj = h.getClass();                               
//					Method m; 
//					
//					m = obj.getMethod(this_method, UserRequest.class,Response.class);
//					m.setAccessible(true); 
//					System.out.println(m); 
//					//////// TEST CODE ////////////
//					//Object[] args = new Object[] { http_request,http_response };
//					Object[] args = new Object[] { http_request,http_response };
//					
//					//////////////////////////////
//					if(m != null) 
//					{//âûçûâàåì 
//						m.invoke(h ,args); 
//					} 
//					h = null; 
//					obj = null; 
//					m = null; 
					/////////////////////////////////////////
					try {
						// ClassA one = new ClassA("string");
						// ClassB two = new ClassB(5);
						 Object h = Class.forName(r.getClassName()).newInstance(); 
					      Object[] input={http_request,http_response};
					      Class cl=Class.forName(r.getClassName());
					      Class[] par=new Class[2];
					      par[0]=UserRequest.class;
					      par[1]=Response.class;
					      Method mthd=cl.getMethod(r.getMethod(),par);
					      
					      mthd.setAccessible(true);
					      
					      mthd.invoke(h,input);
					    //  one.showStr();
					     //two.showNumber();
					      //System.out.println(output.intValue());
					    } catch (Exception e) {
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