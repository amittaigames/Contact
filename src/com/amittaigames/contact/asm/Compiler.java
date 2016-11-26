package com.amittaigames.contact.asm;

import com.amittaigames.contact.Util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

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
			for (String line : lines) {
				String[] args = line.split(" ");

				//
				//	REG
				//
				if (args[0].equals("REG")) {
					dos.writeByte(Bytecode.REG);
					dos.writeByte(getRegisterByName(args[1]));
					writeByType(dos, args[2]);
				}
				
				//
				//	ADD
				//
				else if (args[0].equals("ADD")) {
					dos.writeByte(Bytecode.ADD);
					dos.write(getRegisterByName(args[1]));
					writeByType(dos, args[2]);
				}
				
				//
				//	XCALL
				//
				else if (args[0].equals("XCALL")) {
					dos.write(Bytecode.XCALL);
				}
				
				//
				//	Unknown
				//
				else {
					System.err.println("Unknown instruction: " + args[0]);
				}
			}
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
	
	private static void writeByType(DataOutputStream dos, String str) {
		try {
			DataType type = getType(str);
			if (type == DataType.DECIMAL) {
				dos.writeByte(Bytecode.DATA_DEC);
				dos.writeByte(Byte.parseByte(str.replace("$", "")));
			}
			else {
				dos.writeByte(Bytecode.DATA_REG);
				dos.writeByte(getRegisterByName(str));
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