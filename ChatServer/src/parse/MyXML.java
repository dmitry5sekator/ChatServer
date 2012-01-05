package parse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


public class MyXML 
{
	public static void parse(HashMap <String,String> mass1,String source) throws UnsupportedEncodingException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		byte[] bytes = source.getBytes("UTF-8");
		InputStream is = new ByteArrayInputStream(bytes);
		try
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			NodeList children = root.getChildNodes();
			for(int i = 0;i < children.getLength();i++)
			{
				Node child = children.item(i);
				
				if(child instanceof Element)
				{
					Element childElement = (Element)child;
					Text textNode = (Text) childElement.getFirstChild(); // значение
					String textNode1 = childElement.getNodeName(); // имя
					if(textNode != null)
					{
						String text = textNode.getData().trim();
						mass1.put(textNode1, text);
					}
					
				}
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
	}
	public static String createXML(String rootElementNAME,String childElementNAME,ResultSet set) throws ParserConfigurationException, TransformerFactoryConfigurationError, IOException, TransformerException, SQLException
	{
		OutputStream xmlStream = new  ByteArrayOutputStream();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element rootElement = doc.createElement(rootElementNAME);
		doc.appendChild(rootElement);
		Element childElement = null;
		Text textNode = null;
		//////////////////////////
		while(set.next())
		{
			childElement = doc.createElement(childElementNAME);
			rootElement.appendChild(childElement);
			
			Element nanoChildElement = doc.createElement("id");
			Text textNanoNode = doc.createTextNode(set.getString("id"));
			Element nanoChildElement1 = doc.createElement("name");
			Text textNanoNode1 = doc.createTextNode(set.getString("name"));
			
			childElement.appendChild(nanoChildElement);
			nanoChildElement.appendChild(textNanoNode);
			
			childElement.appendChild(nanoChildElement1);
			nanoChildElement1.appendChild(textNanoNode1);
			
			rootElement.appendChild(childElement);
		}
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(xmlStream)));
		String xml = xmlStream.toString();
		System.out.println(xml);
		return xml;
	}
	public static String createXML_Room_UsersMSG(ResultSet users,ResultSet msg) throws ParserConfigurationException, TransformerFactoryConfigurationError, IOException, TransformerException, SQLException
	{
		OutputStream xmlStream = new  ByteArrayOutputStream();
		File file = new File("testOut.xml");
		file.createNewFile();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element rootElement = doc.createElement("ChatRoomInfo");
		doc.appendChild(rootElement);

		Element childElement = null;
		Element childElementUsers = null;
		Element childElementMessages = null;
		Text textNode = null;
		//////////////////////////
		childElementUsers = doc.createElement("Users");
		rootElement.appendChild(childElementUsers);
		while(users.next())
		{
			childElement = doc.createElement("user");
			childElementUsers.appendChild(childElement);
			
			Element nanoChildElement = doc.createElement("id");
			Text textNanoNode = doc.createTextNode(users.getString("id"));
			Element nanoChildElement1 = doc.createElement("nick");
			Text textNanoNode1 = doc.createTextNode(users.getString("nick"));
			
			childElement.appendChild(nanoChildElement);
			nanoChildElement.appendChild(textNanoNode);
			
			childElement.appendChild(nanoChildElement1);
			nanoChildElement1.appendChild(textNanoNode1);
			
			childElementUsers.appendChild(childElement);
		}
		childElementMessages = doc.createElement("Messages");
		rootElement.appendChild(childElementMessages);
		while(msg.next())
		{
			childElement = doc.createElement("msgs");
			childElementMessages.appendChild(childElement);
			
			Element nanoChildElement = doc.createElement("users_id");
			Text textNanoNode = doc.createTextNode(msg.getString("users_id"));
			Element nanoChildElement1 = doc.createElement("message");
			Text textNanoNode1 = doc.createTextNode(msg.getString("message"));
			
			childElement.appendChild(nanoChildElement);
			nanoChildElement.appendChild(textNanoNode);
			
			childElement.appendChild(nanoChildElement1);
			nanoChildElement1.appendChild(textNanoNode1);
			
			childElementMessages.appendChild(childElement);
		}
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(file)));
		t.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(xmlStream)));
		String xml = xmlStream.toString();
		System.out.println(xml);
		return xml;
	}
	public static String createXML_Room_MSG(ResultSet msg) throws DOMException, SQLException, ParserConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException
	{
		OutputStream xmlStream = new  ByteArrayOutputStream();
		File file = new File("testOut.xml");
		file.createNewFile();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element rootElement = doc.createElement("ChatRoomInfo");
		doc.appendChild(rootElement);

		Element childElement = null;
		Element childElementUsers = null;
		Element childElementMessages = null;
		Text textNode = null;
		//////////////////////////
		
		childElementMessages = doc.createElement("Messages");
		rootElement.appendChild(childElementMessages);
		while(msg.next())
		{
			childElement = doc.createElement("msgs");
			childElementMessages.appendChild(childElement);
			
			Element nanoChildElement = doc.createElement("message");
			Text textNanoNode = doc.createTextNode(msg.getString("message"));
			Element nanoChildElement1 = doc.createElement("users_id");
			Text textNanoNode1 = doc.createTextNode(msg.getString("users_id"));
			
			childElement.appendChild(nanoChildElement);
			nanoChildElement.appendChild(textNanoNode);
			
			childElement.appendChild(nanoChildElement1);
			nanoChildElement1.appendChild(textNanoNode1);
			
			childElementMessages.appendChild(childElement);
		}
		
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(file)));
		t.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(xmlStream)));
		String xml = xmlStream.toString();
		System.out.println(xml);
		return xml;
	}
	public static String createXML_Event_newMsgInRoom(int id_room,int id_user,String msg) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		OutputStream xmlStream = new  ByteArrayOutputStream();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element rootElement = doc.createElement("newMsgInRoom");
		doc.appendChild(rootElement);
		
		Element childElement = null;
		Text textNode = null;
		

		
		Element ChildElement = doc.createElement("id");
		Text textNanoNode = doc.createTextNode(Integer.toString(id_room));
		
		Element ChildElement1 = doc.createElement("msg");
		Text textNanoNode1 = doc.createTextNode(msg);
		
		Element ChildElement2 = doc.createElement("users_id");
		Text textNanoNode2 = doc.createTextNode(Integer.toString(id_user));
	
		rootElement.appendChild(ChildElement);
		ChildElement.appendChild(textNanoNode);
		
		rootElement.appendChild(ChildElement1);
		ChildElement1.appendChild(textNanoNode1);
		
		rootElement.appendChild(ChildElement2);
		ChildElement2.appendChild(textNanoNode2);
		
		Transformer t = TransformerFactory.newInstance().newTransformer();
		
		t.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(xmlStream)));
		String xml = xmlStream.toString();
		return xml;
	}
	public static String createXML_Event_newRoom(int id_room,String name_room) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	{
		OutputStream xmlStream = new  ByteArrayOutputStream();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element rootElement = doc.createElement("newRoom");
		doc.appendChild(rootElement);
		
		Element childElement = null;
		Text textNode = null;
		
		Element ChildElement = doc.createElement("id");
		Text textNanoNode = doc.createTextNode(Integer.toString(id_room));
		
		Element ChildElement1 = doc.createElement("name");
		Text textNanoNode1 = doc.createTextNode(name_room);
		
		
		rootElement.appendChild(ChildElement);
		ChildElement.appendChild(textNanoNode);
		
		rootElement.appendChild(ChildElement1);
		ChildElement1.appendChild(textNanoNode1);
		
		Transformer t = TransformerFactory.newInstance().newTransformer();
		
		t.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(xmlStream)));
		String xml = xmlStream.toString();
		return xml;
	}
	public static String createXML_Event_newUserInRoom(ResultSet users) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, FileNotFoundException, DOMException, SQLException
	{
		OutputStream xmlStream = new  ByteArrayOutputStream();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element rootElement = doc.createElement("newUserInRoom");
		doc.appendChild(rootElement);

		Element childElement = null;
		Element childElementUsers = null;
		Element childElementMessages = null;
		Text textNode = null;

		while(users.next())
		{

			Element nanoChildElement = doc.createElement("id");
			Text textNanoNode = doc.createTextNode(users.getString("id"));
			Element nanoChildElement1 = doc.createElement("nick");
			Text textNanoNode1 = doc.createTextNode(users.getString("nick"));
			
			rootElement.appendChild(nanoChildElement);
			nanoChildElement.appendChild(textNanoNode);
			
			rootElement.appendChild(nanoChildElement1);
			nanoChildElement1.appendChild(textNanoNode1);
			
		}
		
		Transformer t = TransformerFactory.newInstance().newTransformer();
	
		t.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(xmlStream)));
		String xml = xmlStream.toString();
		System.out.println(xml);
		return xml;
	}
}
