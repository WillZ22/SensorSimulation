package action;

import Thread.InsertImageObservationThread;
import Thread.InsertInsuteObservationThread;
import Thread.InsertMoveObservationThread;
import Thread.InsertVideoObservationThread;

public class ButtonAction {
	
	public static boolean start(String sensorType, String sensorname, String sosurl) {
		
		ThreadGroup group = Thread.currentThread().getThreadGroup();  
		ThreadGroup topGroup = group;  
		// 遍历线程组树，获取根线程组  
		while (group != null) {  
		    topGroup = group;  
		    group = group.getParent();  
		}  
		// 激活的线程数加倍  
		int estimatedSize = topGroup.activeCount() * 2;  
		Thread[] slackList = new Thread[estimatedSize];  
		// 获取根线程组的所有线程  
		int actualSize = topGroup.enumerate(slackList);  
		// copy into a list that is the exact size  
		Thread[] list = new Thread[actualSize];  
		System.arraycopy(slackList, 0, list, 0, actualSize);  
		
		for(Thread thread : list) {
			if (thread.getName().equals(sensorname)) {
				return false;
			} 
		}
		
		InsertMoveObservationThread insutThread;
		switch (sensorType) {
		case "GPS":
			InsertMoveObservationThread moveThread = new InsertMoveObservationThread(sensorname, sosurl,"fee","￥");
			moveThread.setName(sensorname);
			moveThread.start();
			break;

		case "视频":
			InsertVideoObservationThread videoThread = new InsertVideoObservationThread(sensorname ,sosurl, "video", "video");
			videoThread.setName(sensorname);
			videoThread.start();
			break;
		case "图像":
			InsertImageObservationThread imageThread = new InsertImageObservationThread(sensorname ,sosurl, "image", "image");
			imageThread.setName(sensorname);
			imageThread.start();
			break;
			
		case "沉降仪":
			insutThread = new InsertMoveObservationThread(sensorname, sosurl,"mm","settlement");
			insutThread.setName(sensorname);
			insutThread.start();
			break;

		case "测斜仪":
			insutThread = new InsertMoveObservationThread(sensorname, sosurl,"°","dip");
			insutThread.setName(sensorname);
			insutThread.start();
			break;

		case "水位传感器":
			insutThread = new InsertMoveObservationThread(sensorname, sosurl,"m","waterlevel");
			insutThread.setName(sensorname);
			insutThread.start();
			break;

		case "雨量传感器":
			insutThread = new InsertMoveObservationThread(sensorname, sosurl,"mm","rainfall");
			insutThread.setName(sensorname);
			insutThread.start();
			break;

		case "温度传感器":
			insutThread = new InsertMoveObservationThread(sensorname, sosurl,"℃","temperature");
			insutThread.setName(sensorname);
			insutThread.start();
			break;

		case "风速传感器":
			insutThread = new InsertMoveObservationThread(sensorname, sosurl,"m/s","windspeed");
			insutThread.setName(sensorname);
			insutThread.start();
			break;

		case "风向传感器":
			insutThread = new InsertMoveObservationThread(sensorname, sosurl,"°","winddirection");
			insutThread.setName(sensorname);
			insutThread.start();
			break;

		case "湿度传感器":
			insutThread = new InsertMoveObservationThread(sensorname, sosurl,"%","");
			insutThread.setName(sensorname);
			insutThread.start();
			break;

		case "CO2浓度传感器":
			insutThread = new InsertMoveObservationThread(sensorname, sosurl,"ppm","");
			insutThread.setName(sensorname);
			insutThread.start();
			break;
		}
		return true;
	}
	
	public static boolean stop(String sensorname) {
		ThreadGroup group = Thread.currentThread().getThreadGroup();  
		ThreadGroup topGroup = group;  
		// 遍历线程组树，获取根线程组  
		while (group != null) {  
		    topGroup = group;  
		    group = group.getParent();  
		}  
		// 激活的线程数加倍  
		int estimatedSize = topGroup.activeCount() * 2;  
		Thread[] slackList = new Thread[estimatedSize];  
		// 获取根线程组的所有线程  
		int actualSize = topGroup.enumerate(slackList);  
		// copy into a list that is the exact size  
		Thread[] list = new Thread[actualSize];  
		System.arraycopy(slackList, 0, list, 0, actualSize);
		
		for(Thread thread : list) {
			if (thread.getName().equals(sensorname)) {
				thread.stop();
				return true;
			} 
		}
		return false;
	}
}
