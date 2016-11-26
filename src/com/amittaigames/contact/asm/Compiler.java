package com.amittaigames.contact.asm;

import com.amittaigames.contact.Util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Compiler {

	private enum DataType {
		INST,
		REG,
		DECIMAL
	}
	
	public static void start(String path) {
		String data = Util.readExternalFile(path);
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(new FileOutputStream(path + "c"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		parse(data, dos);
	}
	
	private static void parse(String data, DataOutputStream dos) {
		try {
			String[] lines = data.split("\n");
			int no = 0;
			
			List<Byte> gen = new ArrayList<>();
			
			for (String line : lines) {
				no++;
				String[] args = line.split(" ");

				//
				//	REG
				//
				if (args[0].equals("REG")) {
					gen.add(Bytecode.REG);
					gen.add(getRegisterByName(args[1]));
					writeByType(gen, args[2]);
				}
				
				//
				//	ADD
				//
				else if (args[0].equals("ADD")) {
					gen.add(Bytecode.ADD);
					gen.add(getRegisterByName(args[1]));
					writeByType(gen, args[2]);
				}
				
				//
				//	XCALL
				//
				else if (args[0].equals("XCALL")) {
					gen.add(Bytecode.XCALL);
				}
				
				//
				//	Unknown
				//
				else {
					System.err.println("Unknown instruction (" + no + "): " + args[0]);
				}
			}
			
			// Move from list to array
			byte[] bytes = new byte[gen.size()];
			for (int i = 0; i < gen.size(); i++) {
				bytes[i] = gen.get(i);
			}
			
			dos.writeInt(gen.size());
			dos.write(bytes, 0, gen.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static byte getRegisterByName(String name) {
		if (name.equals("A"))
			return Bytecode.REG_A;
		if (name.equals("B"))
			return Bytecode.REG_B;
		
		// TODO: Implement the rest of the registers
		
		else
			return Bytecode.NULL;
	}
	
	private static void writeByType(List<Byte> gen, String str) {
		try {
			DataType type = getType(str);
			if (type == DataType.DECIMAL) {
				gen.add(Bytecode.DATA_DEC);
				gen.add(Byte.parseByte(str.replace("$", "")));
			}
			else {
				gen.add(Bytecode.DATA_REG);
				gen.add(getRegisterByName(str));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static DataType getType(String arg) {
		if (arg.startsWith("$"))
			return DataType.DECIMAL;
		else
			return DataType.REG;
	}

}