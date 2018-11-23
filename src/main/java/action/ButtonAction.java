package action;

import Thread.InsertDectorObservationThread;
import Thread.InsertTaxiObservationThread;

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
		
		if (sensorType.equals("移动传感器")) {
			InsertTaxiObservationThread taxiThread = new InsertTaxiObservationThread(sensorname, sosurl);
			taxiThread.setName(sensorname);
			taxiThread.start();
			
		} else if (sensorType.equals("原位传感器")) {
			InsertDectorObservationThread dectorThread = new InsertDectorObservationThread(sensorname, sosurl);
			dectorThread.setName(sensorname);
			dectorThread.start();
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
