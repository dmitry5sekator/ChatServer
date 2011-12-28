package our;



public class Response
{
	String str ="HTTP/1.1 200 OK \n";
	public Response(ResponseCodes Code, String body)
	{
		setResponseCode(Code);
		setBody(body);
	}
	public Response()
	{
		
	}
	
	
	
	
	
	
	public int getMailto() 
	{
		return mailto;
	}
	public void setMailto(int mailto) 
	{
		this.mailto = mailto;
	}
	
	
	
	
	
	
	
	
	
	public ResponseCodes getResponseCode() 
	{
		return responseCode;
	}
	public void setResponseCode(ResponseCodes responseCode) 
	{
		this.responseCode = responseCode;
	}
	
	
	
	
	
	
	
	public String getBody() 
	{
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
	
	
	public String toString()
	{
		//return str+ "Code: "+responseCode.toString()+"\tBody: "+body;
		return str + "\r\ncode:"+ responseCode.toString() +"\r\n"+ body;
	}
	private ResponseCodes responseCode;
	private String body;
	private int mailto;
}