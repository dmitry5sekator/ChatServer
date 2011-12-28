package sender;
import java.net.Socket;


public class SocketMap 
{
	Socket socket;
	String id;
	public SocketMap(Socket socket,String id)
	{
		this.id = id;
		this.socket = socket;
	}
	public Socket getSocket()
	{
		return socket;
	}
	public String getId()
	{
		return id;
	}
}
