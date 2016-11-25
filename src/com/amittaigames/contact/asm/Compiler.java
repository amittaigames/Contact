package com.amittaigames.contact.asm;

import com.amittaigames.contact.Util;

public class Compiler {

	public static void start(String path) {
		String data = Util.readExternalFile(path);
		System.out.println(data);
	}

}