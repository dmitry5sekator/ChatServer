package our;

import java.util.ArrayList;
import java.util.HashMap;


public class UserRequest
{
     /**
	 * 
	 */

    private String body = "";
    private String requestString = "";
    private ArrayList<String> param = new ArrayList<String>();
	public UserRequest()
    {
         
    }
     public UserRequest(String requestString, String body,ArrayList<String> param)
     {
         this.body = body;
         this.requestString = requestString;
         this.param = param;
         
     }
     public void setBody(String body)
     {
    	 this.body = body;
    	 
     }
     public void setRequestString(String requestString)
     {
    	 this.requestString = requestString;
    	 
     }
     public void setParam(ArrayList<String> param)
     {
    	 this.param = param;
    	 
     }
     public String getBody() 
     {
         return body;
     }
     public String getRequestString() 
     {
         return requestString;
     }
     
     public String getParam() 
     {
         return requestString;
     }
     public String AuthLogin()
     {
    	 String temp = "";
    	 for(int i = 0;i < param.size();i++)
    	 {
    		 temp = param.get(i).substring(0, 7);
    		 if(temp.equalsIgnoreCase("Login: "))
    		 {
    			 System.out.println("check");
    			 return param.get(i).substring(param.get(i).indexOf("Login: ")+7,param.get(i).length());
    		 }
    	 }
		return "";
     }
     public String AuthPassword()
     {
    	 String temp = "";
    	 for(int i = 0;i < param.size();i++)
    	 {
    		 temp = param.get(i).substring(0, 10);
    		 if(temp.equalsIgnoreCase("Password: "))
    		 {
    			 System.out.println("check");
    			 return param.get(i).substring(param.get(i).indexOf("Login: ")+11,param.get(i).length());
    		 }
    	 }
		return "";
     }
     public String AuthMD5()
     {
    	 String temp = "";
    	 for(int i = 0;i < param.size();i++)
    	 {
    		 temp = param.get(i).substring(0, 5);
    		 if(temp.equalsIgnoreCase("MD5: "))
    		 {
    			 System.out.println("check");
    			 return param.get(i).substring(param.get(i).indexOf("Login: ")+6,param.get(i).length());
    		 }
    	 }
		return "";
     }
}