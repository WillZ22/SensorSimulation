package utils;

import java.util.ArrayList;
import java.util.List;

public class RunningList {
	public static List<String> runningList = new ArrayList<>();
	
	public void add(String name) {
		runningList.add(name);
	}
	
	public void remove(String name) {
		runningList.remove(name);
	}
}
