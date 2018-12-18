package utils;

import java.util.ArrayList;
import java.util.List;

public class PauseList {
public static List<String> pauseList = new ArrayList<>();
	
	public void add(String name) {
		pauseList.add(name);
	}
	
	public void remove(String name) {
		pauseList.remove(name);
	}
}
