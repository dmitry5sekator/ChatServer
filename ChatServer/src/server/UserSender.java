package server;

import java.net.Socket;
import java.util.HashMap;

public class UserSender 
{
	private volatile static UserSender instance;
    private UserSender() {}
    public static UserSender getInstance() {
        if (instance == null) {
            synchronized (UserSender.class) {
                if (instance == null) {
                    instance = new UserSender();
                }
            }
        }
        return instance;
    }
	
    public static HashMap <String,Socket> SocketMap = new HashMap();
    
    
}
