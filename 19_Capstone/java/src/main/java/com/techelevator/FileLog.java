package com.techelevator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLog {

	public boolean writeToLogFile(String logFile) {

		try {
			FileWriter log = new FileWriter("log.txt", true);
			PrintWriter writer = new PrintWriter(log);
			writer.println(logFile);
			writer.close();
			return true;
		} catch (IOException e) {
			System.out.println("An error occurred.");
		}
		return false;
	}
}
