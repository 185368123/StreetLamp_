package com.shuorigf.bluetooth.streetlamp.ble;

import com.shuorigf.bluetooth.streetlamp.util.LogTools;

public class ModbusData {
	public final static int FC_READ_REG = 0x03;//Function code read reg
	public final static int FC_WRITE_SIGREG = 0x6;//function code -> Write Sing reg
	public final static int FC_WRITE_MUXREG = 0x10;//write mux reg
	public final static int FC_RESET_FACTORY = 0x78;//Restore factory settings
	public final static int FC_CLEAR_HISTORY = 0x79;//Clear history 
	public final static int ER_READ_REG = 0x83;//Function code read reg error
	public final static int ER_WRITE_SIGREG = 0x86;//function code -> Write Sing reg error
	public final static int ER_WRITE_MUXREG = 0x90;//write mux reg error
	public final static int ER_RESET_FACTORY = 0xF8;//Restore factory settings error
	public final static int ER_CLEAR_HISTORY = 0xF9;//Clear history error
	//Exception code

	public final static int EX_NOT_SUPPORT = 0x01;
	public final static int EX_PUD_ADDR_LENGTH = 0x02;
	public final static int EX_REG_RW = 0x03;
	public final static int EX_CLIENT_EXE = 0x04;
	public final static int EX_CRC = 0x05;

	public static byte [] buildRestoreFactoryCmd(int devAddress)
	{
		byte [] bytes = new byte[8];
		bytes[0] = (byte)devAddress;
		bytes[1] = (byte)FC_RESET_FACTORY;
		bytes[2]= (byte)((0x0000 &0xff00) >> 8);
		bytes[3]= (byte)((0x0000 &0xff));
		bytes[4]= (byte)((0x0001 &0xff00) >> 8);
		bytes[5]= (byte)((0x0001 &0xff));
		int crc = ChecksumCRC.calcCrc16(bytes, 0, 6);
		bytes[7]= (byte)((crc &0xff00) >> 8);
		bytes[6]= (byte)((crc &0xff));
		return bytes;
	}
	public static byte [] buildClearHistoryCmd(int devAddress)
	{
		byte [] bytes = new byte[8];
		bytes[0] = (byte)devAddress;
		bytes[1] = (byte)FC_CLEAR_HISTORY;
		bytes[2]= (byte)((0x0000 &0xff00) >> 8);
		bytes[3]= (byte)((0x0000 &0xff));
		bytes[4]= (byte)((0x0001 &0xff00) >> 8);
		bytes[5]= (byte)((0x0001 &0xff));
		int crc = ChecksumCRC.calcCrc16(bytes, 0, 6);
		bytes[7]= (byte)((crc &0xff00) >> 8);
		bytes[6]= (byte)((crc &0xff));
		return bytes;
	}

	public static byte [] buildReadRegsCmd(int devAddress,int start,int count)
	{
		byte [] bytes = new byte[8];
		bytes[0] = (byte)devAddress;
		bytes[1] = (byte)FC_READ_REG;
		bytes[2]= (byte)((start &0xff00) >> 8);
		bytes[3]= (byte)((start &0xff));
		bytes[4]= (byte)((count &0xff00) >> 8);
		bytes[5]= (byte)((count &0xff));
		int crc = ChecksumCRC.calcCrc16(bytes, 0, 6);
		bytes[7]= (byte)((crc &0xff00) >> 8);
		bytes[6]= (byte)((crc &0xff));
		return bytes;
	}

	public static byte [] buildWriteRegCmd(int devAddress,int start,int data)
	{
		byte [] bytes = new byte[8];
		bytes[0] = (byte)devAddress;
		bytes[1] = (byte)FC_WRITE_SIGREG;
		bytes[2]= (byte)((start &0xff00) >> 8);
		bytes[3]= (byte)((start &0xff));
		bytes[4]= (byte)((data &0xff00) >> 8);
		bytes[5]= (byte)((data &0xff));
		int crc = ChecksumCRC.calcCrc16(bytes, 0, 6);
		bytes[7]= (byte)((crc &0xff00) >> 8);
		bytes[6]= (byte)((crc &0xff));
		return bytes;
	}
	public static byte [] buildWriteRegsCmd(int devAddress,int start,int count,int [] data)
	{
		byte [] bytes = new byte[8+data.length*2];
		bytes[0] = (byte)devAddress;
		bytes[1] = (byte)FC_WRITE_MUXREG;
		bytes[2]= (byte)((start &0xff00) >> 8);
		bytes[3]= (byte)((start &0xff));
		bytes[4]= (byte)((count &0xff00) >> 8);
		bytes[5]= (byte)((count &0xff));

		for(int i=0;i<data.length;i++)
		{
			bytes[6+i*2]= (byte)((data[i] &0xff00) >> 8);
			bytes[7+i*2]= (byte)((data[i] &0xff));	
		}

		int crc = ChecksumCRC.calcCrc16(bytes, 0, bytes.length-2);
		bytes[bytes.length-1]= (byte)((crc &0xff00) >> 8);
		bytes[bytes.length-2]= (byte)((crc &0xff));
		return bytes;
	}	
	
	
	public static boolean dataCrcCorrect(byte [] bs)
	{
		if(bs==null ||bs.length < 2)
			return false;
		int crc = ChecksumCRC.calcCrc16(bs, 0, bs.length-2);
		int low = bs[bs.length-2] &(0xff);
		int high = bs[bs.length-1] &(0xff);
		int crc_cmp = (low | (high<<8)) ;
		LogTools.d("dataCrcCorrect","crc:"+ crc+",crc_cmp:"+crc_cmp);
		return (crc==crc_cmp);
	}
	
	
	public static boolean dataCorrect(byte [] bs,int word)
	{
		if(bs== null || bs.length<5)
		{
			return false;
		}
		boolean crc_ok =dataCrcCorrect(bs);
		int b=(0xff)&bs[2];
		
		return (b==word*2)&& crc_ok;
	}

	public static boolean writeSigRegSuccess(byte [] bs)
	{
		if(bs == null || bs.length<1)
		{
			return false;
		}


		return bs[1] == FC_WRITE_SIGREG;
	}

	public static boolean resetFactorySettingsSuccess(byte [] bs)
	{
		if(bs == null || bs.length<1)
		{
			return false;
		}


		return bs[1] == FC_RESET_FACTORY;
	}
}
