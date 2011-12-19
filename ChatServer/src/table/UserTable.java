package table;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;

public class UserTable extends TableGateWay 
{

	public UserTable(Connection conn) 
	{
		super(conn);
		// TODO Auto-generated constructor stub
	}
	//////////////////// SELECT ///////////////////
	public ResultSet findByNick(String nickName)
	{
		String command = "SELECT info FROM users WHERE nickName="+nickName+"";
		return null;
	}
	public ResultSet checkByNickAndPass(String nickName,String password)
	{
		String command = "SELECT nickName FROM"; //here
		return null;
	}
	public ResultSet getAllUsers()
	{
		String command = "SELECT * FROM users";
		return null;
	}
	///////////////////////////////////////////////////
	////////////// INSERT ///////////////////////
	public void createUser(String nickName,String password,String info)
	{
		String command = "INSERT INTO users (nickName,password,info) VALUES("+nickName+","+password+","+info+")";
	}
	/////////////////////////////////////////////
	/////////////// UPDATE ///////////////////////
	public void updateUserPassInfo(String nickName,String password,String info)
	{
		String command = "UPDATE password,info SET password="+password+",info="+info+" WHERE nickName="+nickName+")";
	}
	public void updateUserInfo(String nickName,String info)
	{
		String command = "UPDATE info SET info="+info+" WHERE nickName="+nickName+")";
	}
	public void updateUserPass(String nickName,String pass)
	{
		String command = "UPDATE password SET pass="+pass+" WHERE nickName="+nickName+")";
	}
	/////////////////////////////////////////////
	/////////////////// DELETE /////////////////
	public void deleteUser(String nickName)
	{
		String command = "DELETE FROM users WHERE nickName ="+nickName+"";
	}
	//////////////////////////////////////////
}
