package com.amittaigames.contact.asm;

import com.amittaigames.contact.Util;

public class Run {

	private static byte[] regs = new byte[8];
	
	public static void start(String path) {
		byte[] data = Util.readExternalBytes(path);
		parse(data);
	}
	
	private static void parse(byte[] data) {
		int i = 0;
		int len = data.length;
		
		while (i < len) {
			//
			//	REG
			//
			if (data[i] == Bytecode.REG) {
				byte reg = data[++i];
				byte type = data[++i];
				byte mem = data[++i];
				
				if (type == Bytecode.DATA_DEC)
					regs[reg] = mem;
				else if (type == Bytecode.DATA_REG)
					regs[reg] = regs[mem];

				i++;
				continue;
			}
			
			//
			//	ADD
			//
			else if (data[i] == Bytecode.ADD) {
				byte reg = data[++i];
				byte type = data[++i];
				byte mem = data[++i];
				
				if (type == Bytecode.DATA_DEC)
					regs[reg] += mem;
				else if (type == Bytecode.DATA_REG)
					regs[reg] += regs[mem];
				
				i++;
				continue;
			}
			
			//
			//	XCALL
			//
			else if (data[i] == Bytecode.XCALL) {
				xcall();
				i++;
				continue;
			}
			
			else {
				System.err.println("Corrupted JJC!");
				System.err.println("Byte: " + i);
				System.exit(1);
			}
		}
	}
	
	private static void xcall() {
		if (regs[Bytecode.REG_A] == Bytecode.X_IPRINT) {
			System.out.println(regs[Bytecode.REG_B]);
		}
	}

}
