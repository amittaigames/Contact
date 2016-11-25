package com.amittaigames.contact;

import com.amittaigames.contact.asm.Compiler;

public class Main {
	
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-c")) {
				Compiler.start(args[++i]);
			}
		}
	}

}