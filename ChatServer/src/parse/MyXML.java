package parse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
		//HashMap <String,String> mass1 = new HashMap<String,String>();
		
		//String test = "<?xml version=\"1.0\"?><user><id>-1</id><login>777</login><pass>777</pass><info></info></user>";
		byte[] bytes = source.getBytes("UTF-8");
		InputStream is = new ByteArrayInputStream(bytes);
		//is.read(test.getBytes());
		try
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			//File f = new File("file1.xml");
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
			//for(int i = 0 ; i < mass1.size();i++)
			//System.out.println(mass1.toString());
			//System.out.println(mass1.get("login"));
			//System.out.println(mass1.get("pass"));
			
//			File ff = new File("papka/fdgjdlkgu4g34g3g34g-.tt");
//			ff.createNewFile();
//			//////////////////////////////////////////////
//			
//			String input = "Content-type: text/xml<?xml version=\"1.0\"?><user><id>-1</id><login>777</login><pass>777</pass><info></info></user>";
//			String result = input.substring(input.indexOf("<?xml version="));
//			System.out.println(result);
			

		}
		catch(Exception e)
		{e.printStackTrace();}
	}
	public static String createXML(String rootElementNAME,String childElementNAME,ResultSet set) throws ParserConfigurationException, TransformerFactoryConfigurationError, IOException, TransformerException, SQLException
	{
		OutputStream xmlStream = new  ByteArrayOutputStream();
		//File file = new File("testOut.xml");
		//file.createNewFile();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element rootElement = doc.createElement(rootElementNAME);
		doc.appendChild(rootElement);
//
//		
//		Element childElement = doc.createElement("USER");
//		//Text textNode = doc.createTextNode("4");
//		//doc.appendChild(rootElement);
//		rootElement.appendChild(childElement);
//		//childElement.appendChild(textNode);
//		
//		Element nanoChildElement = doc.createElement("ID");
//		Text textNanoNode = doc.createTextNode("1");
//		
//		childElement.appendChild(nanoChildElement);
//		nanoChildElement.appendChild(textNanoNode);
		
		
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
			
			//textNode = doc.createTextNode(Integer.toString(i));
			//doc.appendChild(rootElement);
			rootElement.appendChild(childElement);
			//childElement.appendChild(textNode);
		}
		//////////////////////////
//		for(int i = 0;i < 4;i++)
//		{
//			childElement = doc.createElement(childElementNAME);
//			rootElement.appendChild(childElement);
//			
//			Element nanoChildElement = doc.createElement("id");
//			Text textNanoNode = doc.createTextNode(Integer.toString(i));
//			Element nanoChildElement1 = doc.createElement("name");
//			Text textNanoNode1 = doc.createTextNode(Integer.toString(i));
//			
//			childElement.appendChild(nanoChildElement);
//			nanoChildElement.appendChild(textNanoNode);
//			
//			childElement.appendChild(nanoChildElement1);
//			nanoChildElement1.appendChild(textNanoNode1);
//			
//			//textNode = doc.createTextNode(Integer.toString(i));
//			//doc.appendChild(rootElement);
//			rootElement.appendChild(childElement);
//			//childElement.appendChild(textNode);
//		}
		
		Transformer t = TransformerFactory.newInstance().newTransformer();
	//	t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(file)));
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
//
//		
//		Element childElement = doc.createElement("USER");
//		//Text textNode = doc.createTextNode("4");
//		//doc.appendChild(rootElement);
//		rootElement.appendChild(childElement);
//		//childElement.appendChild(textNode);
//		
//		Element nanoChildElement = doc.createElement("ID");
//		Text textNanoNode = doc.createTextNode("1");
//		
//		childElement.appendChild(nanoChildElement);
//		nanoChildElement.appendChild(textNanoNode);
		
		
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
			
			//textNode = doc.createTextNode(Integer.toString(i));
			//doc.appendChild(rootElement);
			childElementUsers.appendChild(childElement);
			//childElement.appendChild(textNode);
		}
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
			
			//textNode = doc.createTextNode(Integer.toString(i));
			//doc.appendChild(rootElement);
			childElementMessages.appendChild(childElement);
			//childElement.appendChild(textNode);
		}
		//////////////////////////
//		for(int i = 0;i < 4;i++)
//		{
//			childElement = doc.createElement(childElementNAME);
//			rootElement.appendChild(childElement);
//			
//			Element nanoChildElement = doc.createElement("id");
//			Text textNanoNode = doc.createTextNode(Integer.toString(i));
//			Element nanoChildElement1 = doc.createElement("name");
//			Text textNanoNode1 = doc.createTextNode(Integer.toString(i));
//			
//			childElement.appendChild(nanoChildElement);
//			nanoChildElement.appendChild(textNanoNode);
//			
//			childElement.appendChild(nanoChildElement1);
//			nanoChildElement1.appendChild(textNanoNode1);
//			
//			//textNode = doc.createTextNode(Integer.toString(i));
//			//doc.appendChild(rootElement);
//			rootElement.appendChild(childElement);
//			//childElement.appendChild(textNode);
//		}
		
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
//
//		
//		Element childElement = doc.createElement("USER");
//		//Text textNode = doc.createTextNode("4");
//		//doc.appendChild(rootElement);
//		rootElement.appendChild(childElement);
//		//childElement.appendChild(textNode);
//		
//		Element nanoChildElement = doc.createElement("ID");
//		Text textNanoNode = doc.createTextNode("1");
//		
//		childElement.appendChild(nanoChildElement);
//		nanoChildElement.appendChild(textNanoNode);
		
		
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
			
			//textNode = doc.createTextNode(Integer.toString(i));
			//doc.appendChild(rootElement);
			childElementMessages.appendChild(childElement);
			//childElement.appendChild(textNode);
		}
		//////////////////////////
//		for(int i = 0;i < 4;i++)
//		{
//			childElement = doc.createElement(childElementNAME);
//			rootElement.appendChild(childElement);
//			
//			Element nanoChildElement = doc.createElement("id");
//			Text textNanoNode = doc.createTextNode(Integer.toString(i));
//			Element nanoChildElement1 = doc.createElement("name");
//			Text textNanoNode1 = doc.createTextNode(Integer.toString(i));
//			
//			childElement.appendChild(nanoChildElement);
//			nanoChildElement.appendChild(textNanoNode);
//			
//			childElement.appendChild(nanoChildElement1);
//			nanoChildElement1.appendChild(textNanoNode1);
//			
//			//textNode = doc.createTextNode(Integer.toString(i));
//			//doc.appendChild(rootElement);
//			rootElement.appendChild(childElement);
//			//childElement.appendChild(textNode);
//		}
		
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(file)));
		t.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(xmlStream)));
		String xml = xmlStream.toString();
		System.out.println(xml);
		return xml;
	}
}
