package action;

import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import utils.SendPost;

public class StausChangeAction {

	public void start(String sensorid, String endpoint) {
		String ssnsUrl = endpoint + "/statuschange";
		
		SAXReader reader = new SAXReader();  
        // 通过read方法读取一个文件 转换成Document对象  
        InputStream file=this.getClass().getResourceAsStream("/startsoap.xml");
        Document xmlDoc;
		try {
			xmlDoc = reader.read(file);
	        Element root = xmlDoc.getRootElement();
	        root.element("Body").element("start").element("arg0").setText(sensorid);
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
	
	public void pause(String sensorid, String endpoint) {
		String ssnsUrl = endpoint + "/statuschange";
		
		SAXReader reader = new SAXReader();  
        // 通过read方法读取一个文件 转换成Document对象  
        InputStream file=this.getClass().getResourceAsStream("/pausesoap.xml");
        Document xmlDoc;
		try {
			xmlDoc = reader.read(file);
	        Element root = xmlDoc.getRootElement();
	        root.element("Body").element("pause").element("arg0").setText(sensorid);
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
	
	public void resume(String sensorid, String endpoint) {
		String ssnsUrl = endpoint + "/statuschange";
		
		SAXReader reader = new SAXReader();  
        // 通过read方法读取一个文件 转换成Document对象  
        InputStream file=this.getClass().getResourceAsStream("/resumesoap.xml");
        Document xmlDoc;
		try {
			xmlDoc = reader.read(file);
	        Element root = xmlDoc.getRootElement();
	        root.element("Body").element("resume").element("arg0").setText(sensorid);
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
	
//	public void stop(String sensorid, String endpoint) {
//		String ssnsUrl = endpoint + "/statuschange";
//		
//		SAXReader reader = new SAXReader();  
//        // 通过read方法读取一个文件 转换成Document对象  
//        InputStream file=this.getClass().getResourceAsStream("/stopsoap.xml");
//        Document xmlDoc;
//		try {
//			xmlDoc = reader.read(file);
//	        Element root = xmlDoc.getRootElement();
//	        root.element("Body").element("stop").element("arg0").setText(sensorid);
//	        String xmlStr = xmlDoc.asXML();
//			SendPost.sendPostToSSNS(ssnsUrl, xmlStr);
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
