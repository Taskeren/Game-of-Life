package Taskeren.GameOfLife;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
	
	Properties props;
	
	public String LogFilePath;
	public boolean Random;
	public int RandomNum;
	public int Tick;
	
	public Configuration() {
		props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("Taskeren/GameOfLife/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		LogFilePath = props.getProperty("LogFilePath");
		Random = props.getProperty("Random").equals("true") ? true : false;
		try {
			RandomNum = Integer.parseInt(props.getProperty("RandomNumber"));
			Tick = Integer.parseInt(props.getProperty("Tick"));
		} catch(NumberFormatException e) {
			e.printStackTrace();
			RandomNum = 100;
			Tick = 20;
		}
		
	}
	
}
