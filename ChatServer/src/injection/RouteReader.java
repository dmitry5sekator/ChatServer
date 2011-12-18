package injection;

import java.util.ArrayList;

import controllers.ApplicationController;
import controllers.Route;



public abstract class RouteReader 
{
	ArrayList <Route> r = new ArrayList <Route>();
	public RouteReader(String way) //Путь 
	{
		
	}
	abstract void read(ApplicationController ap);
}
