package controllers;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import our.UserRequest;



public class Route {
	private String pattern;
	private String classname;
	private String method;
	public Route(String pattern,String classname,String method)
	{
		this.pattern = pattern;
		this.classname = classname;
		this.method = method;
	}
	public boolean match(UserRequest httpRequest)
	{
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(httpRequest.getRequestString());
		return m.matches();
	}
	public String getClassName()
	{
		return classname;
	}
	public String getMethod()
	{
		return method;
	}
}