package utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import mainframe.MainFrame;

public class SendPost {
	
	public static String sendPostToSos(String sosUrl ,String xmlStr, String sensorname) throws IOException, DocumentException {
		
        //请求地址
		URL url = new URL(sosUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
		conn.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");
		conn.connect();
		 
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	    out.writeBytes(xmlStr); //写入请求的字符串
	    out.flush();
	    out.close();
	    
	    String status = conn.getResponseMessage();
	        
	    InputStream in=conn.getInputStream();

	    byte[] data1 = new byte[in.available()];
		in.read(data1);
		//转成字符串
		String response = DocumentHelper.parseText(new String(data1)).asXML();	

        
		MainFrame.responseArea.append(sensorname + "：" + status + "     ");
		return response;
	}
	
	
	public static String sendPostToSSNS(String ssnsUrl ,String xmlStr) throws IOException, DocumentException {
		
        //请求地址
		URL url = new URL(ssnsUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
		conn.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");
		conn.connect();
		 
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	    out.writeBytes(xmlStr); //写入请求的字符串
	    out.flush();
	    out.close();
	    
	    String status = conn.getResponseMessage();
	        
	    InputStream in=conn.getInputStream();

	    byte[] data1 = new byte[in.available()];
		in.read(data1);
		//转成字符串
		String response = DocumentHelper.parseText(new String(data1)).asXML();	
		return response;
	}
	
	
}
