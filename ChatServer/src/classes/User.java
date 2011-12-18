package classes;

public class User 
{
	private String nickName;
	private String passWord;
	private String info;
	private String status;
	private int id;
	public User(String nickName,String passWord,String info,String status,int id)
	{
		this.nickName = nickName;
		this.passWord = passWord;
		this.info = info;
		this.status = status;
		this.id = id;
	}
}
