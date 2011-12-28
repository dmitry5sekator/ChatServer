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
	//private HashMap <String,String> map = new HashMap<String,String>(); 
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
//     GET / HTTP/1.1
//     Host: i.ua
//     User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0.1) Gecko/20100101 Firefox/8.0.1
//     Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//     Accept-Language: ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3
//     Accept-Encoding: gzip, deflate
//     Accept-Charset: windows-1251,utf-8;q=0.7,*;q=0.7
//     Connection: keep-alive
//     Cookie: UH=242c384eededb9; US=0; UH=242c384eededb9; US=0
}