package com.amittaigames.contact.asm;

import com.amittaigames.contact.Util;

public class Run {

	private static byte[] regs =  new byte[8];			// 8-bit registers
	private static byte[] stack = new byte[0xFFFF];		// 8-bit stack
	
	public static void start(String path) {
		byte[] data = Util.readExternalBytes(path);
		
		regs[Bytecode.REG_SP] = -1;
		
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
			//	SUB
			//
			else if (data[i] == Bytecode.SUB) {
				byte reg = data[++i];
				byte type = data[++i];
				byte mem = data[++i];
				
				if (type == Bytecode.DATA_DEC)
					regs[reg] -= mem;
				else if (type == Bytecode.DATA_REG)
					regs[reg] -= regs[mem];
				
				i++;
				continue;
			}
			
			//
			//	PUSH
			//
			else if (data[i] == Bytecode.PUSH) {
				byte type = data[++i];
				byte mem = data[++i];
				
				if (type== Bytecode.DATA_DEC)
					push(mem);
				else if (type == Bytecode.DATA_REG)
					push(regs[mem]);
				
				i++;
				continue;
			}
			
			//
			//	POP
			//
			else if (data[i] == Bytecode.POP) {
				byte type = data[++i];
				
				if (type == Bytecode.NULL)
					pop();
				else
					regs[data[++i]] = pop();
				
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
	
	private static void push(byte data) {
		stack[++regs[Bytecode.REG_SP]] = data;
	}
	
	private static byte pop() {
		return stack[regs[Bytecode.REG_SP]--];
	}
	
	private static void xcall() {
		if (regs[Bytecode.REG_A] == Bytecode.X_IPRINT) {
			System.out.println(regs[Bytecode.REG_B]);
		}
	}

}
