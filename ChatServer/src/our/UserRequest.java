package our;

import java.util.HashMap;


public class UserRequest
{
     /**
	 * 
	 */

    private String body = "";
    private String requestString = "";
	private HashMap <String,String> map = new HashMap<String,String>(); 
	public UserRequest()
    {
         
    }
     public UserRequest(String requestString, String body)
     {
         this.setRequestString(requestString);
         this.setBody(body);
     }
     
     public String getBody() {
         return body;
     }
     public void setBody(String body) {
         this.body = body;
     }

     public String getRequestString() {
         return requestString;
     }

     public void setRequestString(String requestString) {
         this.requestString = requestString;
     }
  
}