package action;

import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import utils.SendPost;

public class RegisterAction {

	public void register(String sensorid, String sosAdress, String endpoint) {
		String ssnsUrl = endpoint + "/registerservice";
		
		SAXReader reader = new SAXReader();  
        // 通过read方法读取一个文件 转换成Document对象  
        InputStream file=this.getClass().getResourceAsStream("/registersoap.xml");
        Document xmlDoc;
		try {
			xmlDoc = reader.read(file);
	        Element root = xmlDoc.getRootElement();
	        root.element("Body").element("register").element("arg0").setText(sensorid);
	        root.element("Body").element("register").element("arg1").setText(sosAdress);
	        String xmlStr = xmlDoc.asXML();
			SendPost.sendPostToSSNS(ssnsUrl, xmlStr);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
