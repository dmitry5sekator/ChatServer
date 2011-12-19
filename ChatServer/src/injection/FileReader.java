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
			File file = new File("route_db.rdb");
			Scanner in = new Scanner(file);
			while(in.hasNextLine())
			{
				String line = in.nextLine();
				space[0] = line.indexOf("controllers.")-1;
				space[1] = line.indexOf(" ",space[0]+1);
				//System.out.println(line.substring(0, space[0]) );
				//System.out.println(line.substring(space[0]+1, space[1]));
				ap.addRoute(Pattern.compile(line.substring(0, space[0])), line.substring(space[0]+1, space[1]), line.substring(space[1]+1,line.length()));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
