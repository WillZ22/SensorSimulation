package utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;

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
	
}
