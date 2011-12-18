package parse;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLParser 
{
	public static void parse(String way,HashMap <String,String> mass1)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			File f = new File(way);
			//FileWriter write = new FileWriter(f);
			Document doc = builder.parse(f);
			//HashMap <String,String> mass1 = new HashMap<String,String>();
			Element root = doc.getDocumentElement();
			
			NodeList children = root.getChildNodes();
			for(int i = 0;i < children.getLength();i++)
			{
				Node child = children.item(i);
				if(child instanceof Element)
				{
					Element childElement = (Element)child;
					Text textNode = (Text) childElement.getFirstChild();
					String textNode1 = childElement.getNodeName();
					
					String text = textNode.getData().trim();
					mass1.put(textNode1, text);
				}
			}
			//for(int i = 0 ; i < mass1.size();i++)
				System.out.println(mass1.toString());
		}
		catch(Exception e)
		{e.printStackTrace();}
		
		
	}

	
}
