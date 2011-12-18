package our;

import java.io.Serializable;

public class Response implements Serializable
{
	private static final long serialVersionUID = 1L;
	public Response(ResponseCodes Code, String body)
	{
		setResponseCode(Code);
		setBody(body);
	}
	public Response()
	{
		
	}
	public ResponseCodes getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(ResponseCodes responseCode) {
		this.responseCode = responseCode;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String toString()
	{
		return "Code: "+responseCode.toString()+"\tBody: "+body;
	}
	private ResponseCodes responseCode;
	private String body;
}