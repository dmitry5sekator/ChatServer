package logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Logger 
{
    public Logger() {}
    public static synchronized void writeEvent(String str) throws IOException
	{
    	Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		String time =  sdf.format(cal.getTime());
		
		RandomAccessFile f = new RandomAccessFile("log.txt","rw");
		f.seek(f.length());
		f.writeBytes(time + " : " + str + System.getProperty("line.separator"));
		f.close();
	}
    public static synchronized String getHtml() throws IOException
    {
    	String HTML = "HTTP/1.1 200 OK\r\nServer: Stark\r\nConnection: close\r\nContent-Type: text/html; charset=windows-1251\r\n\r\n";
    	HTML += "<html><head><title>Log from Stark Server</title></head><body bgcolor=\"black\" TEXT=\"lime\"><center><h1>Wellcome Dmitry</h1></center>";
    	Scanner s = new Scanner(new File("log.txt"));
    	while(s.hasNextLine())
    	{
    		HTML += "<p>" + s.nextLine() + "</p>";
    	}
    	HTML += "</body></html>";
		return HTML;
    }
    public static synchronized void clearLog() throws IOException
    {
    	File f = new File("log.txt");
    	f.delete();
    	f.createNewFile();
    }
    
}
