package injection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import logger.Logger;

import server.AuthChecker;
import server.ConnectToDB;
import table.UserTable;

public class AuthCheckerReader 
{
	public AuthCheckerReader()
	{
		
	}
	public void read(AuthChecker target)
	{
		try
		{
			Connection conn = ConnectToDB.getConnection();
			UserTable user = new UserTable(conn);
			ResultSet set = user.getAllLoginPass();
			
			while(set.next())
			{
				target.addLogPass(set.getString("nick"), set.getString("password"));
			}
		}
		catch(SQLException e )
		{
			e.printStackTrace();
			try{Logger.writeEvent(e.toString());}catch(Exception exep){exep.printStackTrace();}
		}
	}
}
