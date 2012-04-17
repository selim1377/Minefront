package com.mime.minefront;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {

	//config path: "res/settings/config.xml";
	static Properties props = new Properties();

	public static void saveConfig(String path, String key, int value) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream write = new FileOutputStream(path);
			props.setProperty(key, Integer.toString(value));
			props.storeToXML(write, "Options");
		} catch (Exception e) {
			System.out.println("Config file creating failed");
		}
	}

	public static int loadConfig(String path, String key) {
		int value = 1;
		try {
			InputStream read = new FileInputStream(path);
			props.loadFromXML(read);
			value = Integer.parseInt(props.getProperty(key));
			read.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {

		}
		return value;
	}

}
