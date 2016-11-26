package com.amittaigames.contact;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;

public class Util {

	public static String readExternalFile(String path) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	public static byte[] readExternalBytes(String path) {
		byte[] data = null;
		
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(path));
			int len = dis.readInt();
			data = new byte[len];
			dis.readFully(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return data;
	}
	
}