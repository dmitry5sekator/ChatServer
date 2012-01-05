package logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    
}
