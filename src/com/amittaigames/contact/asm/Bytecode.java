package com.amittaigames.contact.asm;

import java.util.HashMap;

public class Bytecode {

	public static final byte NULL = 0;
	
	/*
			
			INSTRUCTIONS
			
	 */
	
	public static final byte XCALL = 		1;
	
	//
	// 2-9 RESERVED
	//
	
	public static final byte REG = 			10;
	public static final byte ADD = 			11;
	public static final byte SUB = 			12;
	public static final byte MUL = 			13;
	public static final byte DIV = 			14;
	
	
	
	/*
	
			REGISTERS
	
	 */
	
	public static final byte REG_A = 		1;
	public static final byte REG_B = 		2;
	public static final byte REG_C = 		3;
	public static final byte REG_D = 		4;
	public static final byte REG_IP = 		5;
	public static final byte REG_SP = 		6;
	public static final byte REG_BP =		7;
	
	
	/*
	
			DATA TYPES
	
	 */
	
	public static final byte DATA_REG =		1;
	public static final byte DATA_DEC =		2;
	
}