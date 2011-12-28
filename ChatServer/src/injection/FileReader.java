package injection;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

import controllers.ApplicationController;



public class FileReader extends RouteReader 
{
	private int [] space = new int[2];
	public FileReader(String way) 
	{
		// TODO Auto-generated constructor stub
		super(way);
	}

	@Override
	public void read(ApplicationController ap) 
	{
		// TODO Auto-generated method stub
		try
		{
			String patt [] = {"(POST /chatroom/member/ HTTP/1.1)","(POST /chatroom/ HTTP/1.1)","(POST /chatroom/message/ HTTP/1.1)","(GET /chatroom/)[\\d]{1,6}(/messages/ HTTP/1.1)","(POST /users/signIn/ HTTP/1.1)","(POST /users/ HTTP/1.1)"};
			File file = new File("route_db.txt");
			Scanner in = new Scanner(file);
			int i = 0;
			while(in.hasNextLine())
			{
				String line = in.nextLine();
				space[0] = line.indexOf("controllers.")-1;
				space[1] = line.indexOf(" ",space[0]+1);
				
				String pattern = line.substring(0, space[0]);
				String classname = line.substring(space[0]+1, space[1]);
				String methodname = line.substring(space[1]+1,line.length());
				System.out.println(patt[i] + " " + methodname);
				//ap.addRoute(Pattern.compile(line.substring(0, space[0])), line.substring(space[0]+1, space[1]), line.substring(space[1]+1,line.length()));
				ap.addRoute(patt[i], classname, methodname);
				i++;
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
