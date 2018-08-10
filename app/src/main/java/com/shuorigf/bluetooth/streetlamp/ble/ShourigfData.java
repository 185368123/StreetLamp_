package com.shuorigf.bluetooth.streetlamp.ble;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShourigfData {
	public final static int REG_LIGHT_CONTROL_DELAY_ADDR=0xE046;
	public final static int REG_LIGHT_CONTROL_VOLTAGE_ADDR=0xE047;
	public final static int REG_LOAD_CURRENT_ADDR=0xE048;
	public final static int REG_COLOR_LIGHT_SOURCE_ADDR=0xE049;
	public final static int REG_SMART_POWER_ADDR=0xE04A;
	public final static int REG_TEMPERATURE_PROTECTION_ADDR=0xE04B;
	public final static int REG_INDUCTION_DISTANCE_ADDR=0xE04C;
	public final static int REG_MUSIC_SWITCH_ADDR=0xE04D;

	public final static int REG_OVER_CHARGE_VOLTAGE_ADDR=0xE008;
	public final static int REG_OVER_CHARGE_RETURN_ADDR=0xE00A;
	public final static int REG_OVER_DISCHARGE_RETURN_ADDR=0xE00B;
	public final static int REG_OVER_DISCHARGE_VOLTAGE_ADDR=0xE00D;

	public final static int REG_L_FIRST_TIME_ADDR=0xE015;
	public final static int REG_L_FIRST_POWER_ADDR=0xE016;
	public final static int REG_L_FIRST_COLOR_TEMPERATURE_ADDR=0xE017;
	public final static int REG_L_SECOND_TIME_ADDR=0xE018;
	public final static int REG_L_SECOND_POWER_ADDR=0xE019;
	public final static int REG_L_SECOND_COLOR_TEMPERATURE_ADDR=0xE01A;
	public final static int REG_L_THREE_TIME_ADDR=0xE01B;
	public final static int REG_L_THREE_POWER_ADDR=0xE01C;
	public final static int REG_L_THREE_COLOR_TEMPERATURE_ADDR=0xE01D;
	public final static int REG_L_FOUR_TIME_ADDR=0xE01E;
	public final static int REG_L_FOUR_POWER_ADDR=0xE01F;
	public final static int REG_L_FOUR_COLOR_TEMPERATURE_ADDR=0xE020;

	public final static int REG_T_FIRST_TIME_ADDR=0xE021;
	public final static int REG_T_FIRST_POWER_ADDR=0xE022;
	public final static int REG_T_FIRST_COLOR_TEMPERATURE_ADDR=0xE023;
	public final static int REG_T_SECOND_TIME_ADDR=0xE024;
	public final static int REG_T_SECOND_POWER_ADDR=0xE025;
	public final static int REG_T_SECOND_COLOR_TEMPERATURE_ADDR=0xE026;
	public final static int REG_T_THREE_TIME_ADDR=0xE027;
	public final static int REG_T_THREE_POWER_ADDR=0xE028;
	public final static int REG_T_THREE_COLOR_TEMPERATURE_ADDR=0xE029;
	public final static int REG_T_MORNING_TIME_ADDR=0xE02A;
	public final static int REG_T_MORNING_POWER_ADDR=0xE02B;
	public final static int REG_T_MORNING_COLOR_TEMPERATURE_ADDR=0xE02C;


	public final static int REG_M_FIRST_TIME_ADDR=0xE02D;
	public final static int REG_M_FIRST_POWER_ADDR=0xE02E;
	public final static int REG_M_FIRST_COLOR_TEMPERATURE_ADDR=0xE02F;
	public final static int REG_M_SECOND_TIME_ADDR=0xE030;
	public final static int REG_M_HAVE_ONE_POWER_ADDR=0xE031;
	public final static int REG_M_HAVE_ONE_COLOR_TEMPERATURE_ADDR=0xE032;
	public final static int REG_M_INDUCTION_DELAY_ADDR=0xE033;
	public final static int REG_M_NO_ONE_POWER_ADDR=0xE034;
	public final static int REG_M_HAVE_TWO_POWER_ADDR=0xE035;
	public final static int REG_M_HAVE_TWO_COLOR_TEMPERATURE_ADDR=0xE036;
	public final static int REG_M_NO_TWO_POWER_ADDR=0xE037;
	public final static int REG_M_NO_PEOPLE_COLOR_TEMPERATURE_ADDR=0xE038;

	public final static int REG_U_FIRST_TIME_ADDR=0xE39;
	public final static int REG_U_FIRST_POWER_ADDR=0xE03A;
	public final static int REG_U_FIRST_COLOR_TEMPERATURE_ADDR=0xE03B;
	public final static int REG_U_SECOND_TIME_ADDR=0xE03C;
	public final static int REG_U_SECOND_POWER_ADDR=0xE03D;
	public final static int REG_U_SECOND_COLOR_TEMPERATURE_ADDR=0xE03E;
	public final static int REG_U_THREE_TIME_ADDR=0xE03F;
	public final static int REG_U_THREE_POWER_ADDR=0xE040;
	public final static int REG_U_THREE_COLOR_TEMPERATURE_ADDR=0xE041;
	public final static int REG_U_INDUCTION_DELAY_ADDR=0xE042;
	public final static int REG_U_NO_PEOPLE_POWER_ADDR=0xE043;
	public final static int REG_U_NO_PEOPLE_COLOR_TEMPERATURE_ADDR=0xE044;

	public final static int REG_LAMP_BRIGHTNESS_ADDR=0xE001;
	public final static int REG_MODE_ADDR=0xE045;

	public static final int STATUS_COLOR_LIGHT_SOURCE_FLICKER = 1;
	public static final int STATUS_COLOR_LIGHT_SOURCE_RED = 2;
	public static final int STATUS_COLOR_LIGHT_SOURCE_GREEN = 3;
	public static final int STATUS_COLOR_LIGHT_SOURCE_BLUE = 4;
	public static final int STATUS_COLOR_LIGHT_SOURCE_RED_GREEN = 5;
	public static final int STATUS_COLOR_LIGHT_SOURCE_RED_BLUE= 6;
	public static final int STATUS_COLOR_LIGHT_SOURCE_GREEN_BLUE= 7;
	public static final int STATUS_COLOR_LIGHT_SOURCE_PUT_OUT= 15;

	public static final int STATUS_CLOSE = 0;
	public static final int STATUS_ONE_LEVEL = 1;
	public static final int STATUS_TWO_LEVEL = 2;
	public static final int STATUS_THREE_LEVEL = 3;
	public static final int STATUS_FOUR_LEVEL = 4;
	public static final int STATUS_FIVE_LEVEL = 5;
	public static final int STATUS_SIX_LEVEL= 6;
	public static final int STATUS_SEVEN_LEVEL= 7;
	public static final int STATUS_EIGHT_LEVEL= 8;
	public static final int STATUS_AUTOMATIC = 255;

	public static final int STATUS_SWITCH_CLOSE = 0;
	public static final int STATUS_SWITCH_OPEN = 1;

	public static final int STATUS_COLOR_TEMPERATURE_AUTOMATIC = 0;
	public static final int STATUS_COLOR_TEMPERATURE_WARM_WHITE = 10;
	public static final int STATUS_COLOR_TEMPERATURE_NATURAL_WHITE = 50;
	public static final int STATUS_COLOR_TEMPERATURE_COOL_WHITE = 100;

	public static final int STATUS_BATTERY_NORMAL = 0;
	public static final int STATUS_BATTERY_CHARGING = 1;
	public static final int STATUS_BATTERY_DISCHARGING = 2;
	public static final int STATUS_BATTERY_ABNORMAL = 3;

	public static final int MODE_L= 0;
	public static final int MODE_T = 1;
	public static final int MODE_M = 2;
	public static final int MODE_U = 3;


	public static int Bytes2Int(byte []bs,int offset,int len)
	{ 
		int ret = 0;
		if(bs.length < offset+len )//error
			return ret;

		for(int i=0;i<len;i++)
		{
			ret |= (bs[offset+i] & 0xff) << (8*(len-i-1));
		}
		return ret;
	}
	public static long  Bytes2Long(byte []bs,int offset,int len)
	{ 
		long ret = 0;
		if(bs.length < offset+len )//error
			return ret;

		for(int i=0;i<len;i++)
		{
			ret |= (bs[offset+i] & 0xff) << (8*(len-i-1));
		}
		return ret;
	}
	public static String Bytes2String(byte []bs,int offset,int len)
	{ 
		byte [] temp_data = new byte[len];
		String ret = "";
		if(bs.length < offset+len )//error
			return ret;
		System.arraycopy(bs,offset,temp_data,0,len);
		ret = new String(temp_data);
		return ret;
	}
	static public class FaultAndWarnInfo{
		public final static int REG_ADDR=0x0121;
		public final static int READ_WORD=0x0002;
		public List<Boolean> mList = new ArrayList<>();
		private boolean mDataIsCorrect=false;
		public FaultAndWarnInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mList.add((bs[4]&0x01) != 0);//b16
				mList.add((bs[4]&0x02) != 0);//b17
				mList.add((bs[4]&0x04) != 0);//b18
				mList.add((bs[4]&0x08) != 0);//b19
				mList.add((bs[4]&0x10) != 0);//b20
				mList.add((bs[4]&0x20) != 0);//b21
				mList.add((bs[4]&0x40) != 0);//b22
				mList.add((bs[4]&0x80) != 0);//b23
				mList.add((bs[3]&0x01) != 0);//b24
				mList.add((bs[3]&0x02) != 0);//b25
				mList.add((bs[3]&0x04) != 0);//b26
				mList.add((bs[3]&0x08) != 0);//b27
				mList.add((bs[3]&0x10) != 0);//b28
				mList.add((bs[3]&0x20) != 0);//b29
				mList.add((bs[3]&0x40) != 0);//b30
			}
		}
	};

	static public class SolarPanelInfo{
		public final static int REG_ADDR=0x0107;
		public final static int READ_WORD=0x0003;
		public float mVoltage;//0.1V
		public float mCurrent;//0.1A
		public float mChargingPower;//W
		private boolean mDataIsCorrect=false;
		public SolarPanelInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mVoltage = (new BigDecimal(Bytes2Int(bs,3,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mCurrent = (new BigDecimal(Bytes2Int(bs,5,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mChargingPower =  ((new BigDecimal(Bytes2Int(bs,3,2) + "").multiply(new BigDecimal("0.01"))))
						.multiply((new BigDecimal(Bytes2Int(bs,5,2) + "").multiply(new BigDecimal("0.01")))).floatValue();
			}
		}
	};	

	/*	static public class BatteryEsidualCapacity{
		public final static int REG_ADDR=0x0100;
		public final static int READ_WORD=0x0001;
		public int mCapacity=0;//%

		private boolean mDataIsCorrect=false;
		public BatteryEsidualCapacity(byte []bs)
		{
			mDataIsCorrect = ModbusData.DataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mCapacity = Bytes2Int(bs,3,2);
			}
		}
	};	

	static public class BatteryVoltage{
		public final static int REG_ADDR=0x0101;
		public final static int READ_WORD=0x0001;
		public float mVoltage=0;//0.1V

		private boolean mDataIsCorrect=false;
		public BatteryVoltage(byte []bs)
		{
			mDataIsCorrect = ModbusData.DataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mVoltage = Bytes2Int(bs,3,2)*0.1f;
			}
		}
	};	
	 */
	static public class BatteryInfo{
		public final static int REG_ADDR=0x0100;
		public final static int READ_WORD=0x0004;
		public int mElectricity;//%
		public float mVoltage;//0.1V
		public float mCurrent;
		public int mBatteryTemperature;
		public int mDeviceTemperature;
		private boolean mDataIsCorrect=false;

		public BatteryInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mElectricity = Bytes2Int(bs,3,2);
				mVoltage = (new BigDecimal(Bytes2Int(bs,5,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mCurrent = (new BigDecimal(Bytes2Int(bs,7,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				if ((bs[9]&0x80) != 0){
					mDeviceTemperature = -(bs[9]&0x7f);
				}else {
					mDeviceTemperature = bs[9]&0x7f;
				}
				if ((bs[10]&0x80) != 0){
					mBatteryTemperature = -(bs[10]&0x7f);
				}else {
					mBatteryTemperature = bs[10]&0x7f;
				}
			}
		}
	};

	static public class LoadInfo{
		public final static int REG_ADDR=0x0104;
		public final static int READ_WORD=0x0003;
		public float mVoltage;
		public float mCurrent;
		public float mLoadPower;
		private boolean mDataIsCorrect=false;

		public LoadInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mVoltage = (new BigDecimal(Bytes2Int(bs,3,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mCurrent = (new BigDecimal(Bytes2Int(bs,5,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mLoadPower =  ((new BigDecimal(Bytes2Int(bs,3,2) + "").multiply(new BigDecimal("0.01"))))
						.multiply((new BigDecimal(Bytes2Int(bs,5,2) + "").multiply(new BigDecimal("0.01")))).floatValue();

			}
		}
	};

	static public class BatteryState{
		public final static int REG_ADDR=0x0120;
		public final static int READ_WORD=0x0001;
		public int mLampBrightness;
		public int mBatteryState;
		private boolean mDataIsCorrect=false;
		public BatteryState(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mLampBrightness = bs[3]&0x7f;
				mBatteryState = Bytes2Int(bs,4,1);
				if(mBatteryState>6)
					mBatteryState = 0;
			}
		}
	};


	/*about device info*/
	static public class DeviceProductType{
		public final static int REG_ADDR=0x000C;
		public final static int READ_WORD=0x0008;
		public String mProductTypeStr="";
		private boolean mDataIsCorrect=false;
		public DeviceProductType(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mProductTypeStr = Bytes2String(bs,3,16).replaceAll(" ", "");
			}
		}
	};	
	static public class DeviceVersionInfo{
		public final static int REG_ADDR=0x0014;
		public final static int READ_WORD=0x0004;
		public String mSoftVersion="";
		public String mHardwareVersion="";
		private boolean mDataIsCorrect=false;
		public DeviceVersionInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mSoftVersion = "V"+bs[4]+"."+bs[5]+"."+bs[6];
				mHardwareVersion = "V"+bs[8]+"."+bs[9]+"."+bs[10];
			}
		}
	};	
//	static public class DeviceSerialNumber{
//		public final static int REG_ADDR=0x0018;
//		public final static int READ_WORD=0x0003;
//		public String mSN="150100011";
//		public int mDeviceAddr = 0;
//
//		private boolean mDataIsCorrect=false;
//		public DeviceSerialNumber(byte []bs)
//		{
//			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
//			if(mDataIsCorrect)
//			{
//				mSN = String.format("%02d%02d%04d", Bytes2Int(bs,3,1),Bytes2Int(bs,4,1),Bytes2Int(bs,5,2));
//				mDeviceAddr =  Bytes2Int(bs,0,1);
//			}
//		}
//	};


	static public class LModeInfo{
		public final static int REG_ADDR=0xE015;
		public final static int READ_WORD=0x000C;
		public int mFirstTime;
		public int mFirstPower;
		public int mFirstColorTemperature;
		public int mSecondTime;
		public int mSecondPower;
		public int mSecondColorTemperature;
		public int mThreeTime;
		public int mThreePower;
		public int mThreeColorTemperature;
		public int mFourTime;
		public int mFourPower;
		public int mFourColorTemperature;

		private boolean mDataIsCorrect=false;
		public LModeInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mFirstTime =  Bytes2Int(bs,3,2);
				mFirstPower = Bytes2Int(bs,5,2);
				mFirstColorTemperature = Bytes2Int(bs,7,2);
				mSecondTime =  Bytes2Int(bs,9,2);
				mSecondPower = Bytes2Int(bs,11,2);
				mSecondColorTemperature = Bytes2Int(bs,13,2);
				mThreeTime =  Bytes2Int(bs,15,2);
				mThreePower = Bytes2Int(bs,17,2);
				mThreeColorTemperature = Bytes2Int(bs,19,2);
				mFourTime =  Bytes2Int(bs,21,2);
				mFourPower = Bytes2Int(bs,23,2);
				mFourColorTemperature = Bytes2Int(bs,25,2);

			}
		}
	};

	static public class TModeInfo{
		public final static int REG_ADDR=0xE021;
		public final static int READ_WORD=0x000C;
		public int mFirstTime;
		public int mFirstPower;
		public int mFirstColorTemperature;
		public int mSecondTime;
		public int mSecondPower;
		public int mSecondColorTemperature;
		public int mThreeTime;
		public int mThreePower;
		public int mThreeColorTemperature;
		public int mMorningTime;
		public int mMorningPower;
		public int mMorningColorTemperature;

		private boolean mDataIsCorrect=false;
		public TModeInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mFirstTime =  Bytes2Int(bs,3,2);
				mFirstPower = Bytes2Int(bs,5,2);
				mFirstColorTemperature = Bytes2Int(bs,7,2);
				mSecondTime =  Bytes2Int(bs,9,2);
				mSecondPower = Bytes2Int(bs,11,2);
				mSecondColorTemperature = Bytes2Int(bs,13,2);
				mThreeTime =  Bytes2Int(bs,15,2);
				mThreePower = Bytes2Int(bs,17,2);
				mThreeColorTemperature = Bytes2Int(bs,19,2);
				mMorningTime =  Bytes2Int(bs,21,2);
				mMorningPower = Bytes2Int(bs,23,2);
				mMorningColorTemperature = Bytes2Int(bs,25,2);

			}
		}
	};

	static public class MModeInfo{
		public final static int REG_ADDR=0xE02D;
		public final static int READ_WORD=0x000C;
		public int mFirstTime;
		public int mFirstPower;
		public int mFirstColorTemperature;
		public int mSecondTime;
		public int mHaveOnePower;
		public int mHaveOneColorTemperature;
		public int mInductionDelay;
		public int mNoOnePower;
		public int mHaveTwoPower;
		public int mHaveTwoColorTemperature;
		public int mNoTwoPower;
		public int mNoPeopleColorTemperature;

		private boolean mDataIsCorrect=false;
		public MModeInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mFirstTime =  Bytes2Int(bs,3,2);
				mFirstPower = Bytes2Int(bs,5,2);
				mFirstColorTemperature = Bytes2Int(bs,7,2);
				mSecondTime =  Bytes2Int(bs,9,2);
				mHaveOnePower = Bytes2Int(bs,11,2);
				mHaveOneColorTemperature = Bytes2Int(bs,13,2);
				mInductionDelay =  Bytes2Int(bs,15,2);
				mNoOnePower = Bytes2Int(bs,17,2);
				mHaveTwoPower = Bytes2Int(bs,19,2);
				mHaveTwoColorTemperature =  Bytes2Int(bs,21,2);
				mNoTwoPower = Bytes2Int(bs,23,2);
				mNoPeopleColorTemperature = Bytes2Int(bs,25,2);

			}
		}
	};

	static public class UModeInfo{
		public final static int REG_ADDR=0xE039;
		public final static int READ_WORD=0x000C;
		public int mFirstTime;
		public int mFirstPower;
		public int mFirstColorTemperature;
		public int mSecondTime;
		public int mSecondPower;
		public int mSecondColorTemperature;
		public int mThreeTime;
		public int mThreePower;
		public int mThreeColorTemperature;
		public int mInductionDelay;
		public int mNoPeoplePower;
		public int mNoPeopleColorTemperature;

		private boolean mDataIsCorrect=false;
		public UModeInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mFirstTime =  Bytes2Int(bs,3,2);
				mFirstPower = Bytes2Int(bs,5,2);
				mFirstColorTemperature = Bytes2Int(bs,7,2);
				mSecondTime =  Bytes2Int(bs,9,2);
				mSecondPower = Bytes2Int(bs,11,2);
				mSecondColorTemperature = Bytes2Int(bs,13,2);
				mThreeTime =  Bytes2Int(bs,15,2);
				mThreePower = Bytes2Int(bs,17,2);
				mThreeColorTemperature = Bytes2Int(bs,19,2);
				mInductionDelay =  Bytes2Int(bs,21,2);
				mNoPeoplePower = Bytes2Int(bs,23,2);
				mNoPeopleColorTemperature = Bytes2Int(bs,25,2);

			}
		}
	};

	static public class ReadMode{
		public final static int REG_ADDR=0xE045;
		public final static int READ_WORD=0x0001;
		public int mMode;
		private boolean mDataIsCorrect=false;
		public ReadMode(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mMode = Bytes2Int(bs,3,2);
			}
		}
	};

	static public class OverInfo{
		public final static int REG_ADDR=0xE008;
		public final static int READ_WORD=0x0006;
		public float mOverchargeVoltage;
		public float mOverchargeReturn;
		public float mOverDischargeReturn;
		public float mOverDischargeVoltage;
		private boolean mDataIsCorrect=false;
		public OverInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mOverchargeVoltage = (new BigDecimal(Bytes2Int(bs,3,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mOverchargeReturn = (new BigDecimal(Bytes2Int(bs,7,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mOverDischargeReturn = (new BigDecimal(Bytes2Int(bs,9,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mOverDischargeVoltage = (new BigDecimal(Bytes2Int(bs,13,2) + "").multiply(new BigDecimal("0.01"))).floatValue();

			}
		}
	};

	static public class WorkModeContentInfo{
		public final static int REG_ADDR=0xE046;
		public final static int READ_WORD=0x0008;
		public int mLightControlDelay;
		public float mLightControlVoltage;
		public int mLoadCurrent;
		public int mColorLightSource;
		public int mSmartPower;
		public int mTemperatureProtection;
		public int mInductionDistance;
		public int mMusicSwitch;

		private boolean mDataIsCorrect=false;
		public WorkModeContentInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mLightControlDelay = Bytes2Int(bs,3,2);
				mLightControlVoltage = (new BigDecimal(Bytes2Int(bs,5,2) + "").multiply(new BigDecimal("0.1"))).floatValue();
				mLoadCurrent = Bytes2Int(bs,7,2)*10;
				mColorLightSource = Bytes2Int(bs,9,2);
				mSmartPower = Bytes2Int(bs,11,2);
				mTemperatureProtection = Bytes2Int(bs,13,2);
				mInductionDistance = Bytes2Int(bs,15,2);
				mMusicSwitch = Bytes2Int(bs,17,2);

			}
		}
	};


	/**historicaldata*/
	static public class HistoricalDataInfo{
		public final static int REG_ADDR=0x010B;
		public final static int READ_WORD=0x0015;
		public float mDayBatteryMinVoltage;
		public float mDayBatteryMaxVoltage;
        public float mDayChargeMaxCurrent;
        public float mDayDischargeMaxCurrent;
		public int mDayChargeMaxPower;
		public int mDayDischargeMaxPower;
		public float mDayChargeAmpHour;
		public float mDayDischargeAmpHour;
        public int mDayMaxTemperature;
        public int mDayMinTemperature;
		public int mAllRunDays;
		public long mBatteryAllDischargeTimes;
		public long mBatteryChargeFullTimes;
		public float mBatteryChargeAllApmHour;
		public float mBatteryDischargeAllApmHour;
        public float mAccumulativeGeneratingCapacity;
        public float mAccumulativePowerConsumption;

		private boolean mDataIsCorrect=false;
		public HistoricalDataInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
                mDayBatteryMinVoltage =  (new BigDecimal(Bytes2Int(bs,3,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mDayBatteryMaxVoltage =  (new BigDecimal(Bytes2Int(bs,5,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
                mDayChargeMaxCurrent = (new BigDecimal(Bytes2Int(bs,7,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
                mDayDischargeMaxCurrent = (new BigDecimal(Bytes2Int(bs,9,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
                mDayChargeMaxPower = Bytes2Int(bs,11,2);
                mDayDischargeMaxPower = Bytes2Int(bs,13,2);
                mDayChargeAmpHour = (new BigDecimal(Bytes2Int(bs,15,2) + "").multiply(new BigDecimal("0.001"))).floatValue();
                mDayDischargeAmpHour = (new BigDecimal(Bytes2Int(bs,17,2) + "").multiply(new BigDecimal("0.001"))).floatValue();
                mDayMaxTemperature = Bytes2Int(bs,19,2);
                mDayMinTemperature = Bytes2Int(bs,21,2);
                mAllRunDays = Bytes2Int(bs,23,2);
                mBatteryAllDischargeTimes = Bytes2Long(bs,25,2);
                mBatteryChargeFullTimes = Bytes2Long(bs,27,2);
                mBatteryChargeAllApmHour = (new BigDecimal(Bytes2Long(bs,29,4) + "").multiply(new BigDecimal("0.001"))).floatValue();
                mBatteryDischargeAllApmHour = (new BigDecimal(Bytes2Long(bs,33,4) + "").multiply(new BigDecimal("0.001"))).floatValue();
                mAccumulativeGeneratingCapacity =  (new BigDecimal(Bytes2Long(bs,37,4) + "").multiply(new BigDecimal("0.001"))).floatValue();
                mAccumulativePowerConsumption =  (new BigDecimal(Bytes2Long(bs,41,4) + "").multiply(new BigDecimal("0.001"))).floatValue();
            }
		}
	};
	static public class BatteryTemperatureInfo{
		public final static int REG_ADDR=0x0123;
		public final static int READ_WORD=0x0001;
		public int mControlBoardTemperature ;
		public int mProtectivePlateTemperature;
		private boolean mDataIsCorrect=false;

		public BatteryTemperatureInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				if ((bs[3]&0x80) != 0){
					mControlBoardTemperature = -(bs[3]&0x7f);
				}else {
					mControlBoardTemperature = bs[3]&0x7f;
				}
				if ((bs[4]&0x80) != 0){
					mProtectivePlateTemperature = -(bs[4]&0x7f);
				}else {
					mProtectivePlateTemperature = bs[4]&0x7f;
				}
			}
		}
	};


	/**historicalChartData*/
	static public class HistoricalChartDataInfo{
		public final static int REG_ADDR=0xF000;
		public final static int READ_WORD=0x0001;
		public final static int READ_WRONG=0xF002;
		public float mDayBatteryMinVoltage;
		public float mDayBatteryMaxVoltage;
		public int mDayMaxTemperature;
		public int mDayMinTemperature;
		public float mDayChargeAmpHour;
		public float mDayDischargeAmpHour;
		public float mAccumulativeGeneratingCapacity;
		public float mAccumulativePowerConsumption;
        public boolean mDataIsCorrect=false;
		public HistoricalChartDataInfo(byte []bs)
		{
			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
			if(mDataIsCorrect)
			{
				mDayBatteryMinVoltage =  (new BigDecimal(Bytes2Int(bs,3,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mDayBatteryMaxVoltage =  (new BigDecimal(Bytes2Int(bs,5,2) + "").multiply(new BigDecimal("0.01"))).floatValue();
				mDayChargeAmpHour = (new BigDecimal(Bytes2Int(bs,15,2) + "").multiply(new BigDecimal("0.001"))).floatValue();
				mDayDischargeAmpHour = (new BigDecimal(Bytes2Int(bs,17,2) + "").multiply(new BigDecimal("0.001"))).floatValue();
				mDayMaxTemperature = Bytes2Int(bs,19,2);
				mDayMinTemperature = Bytes2Int(bs,21,2);
				mAccumulativeGeneratingCapacity =  (new BigDecimal(Bytes2Long(bs,37,4) + "").multiply(new BigDecimal("0.001"))).floatValue();
				mAccumulativePowerConsumption =  (new BigDecimal(Bytes2Long(bs,41,4) + "").multiply(new BigDecimal("0.001"))).floatValue();
			}
		}
	};	
	
//	/*param settings*/
//
//	static public class ParamSettingData{
//		public final static int REG_ADDR=0xE001;
//		public final static int READ_WORD=0x0021;
//		public int mData[] = new int [READ_WORD];
//
//		private boolean mDataIsCorrect=false;
//		public ParamSettingData(byte []bs)
//		{
//			mDataIsCorrect = ModbusData.dataCorrect(bs,READ_WORD);
//			if(mDataIsCorrect)
//			{
//				for(int i=0;i<mData.length;i++)
//				{
//					mData[i] = Bytes2Int(bs,3+i*2,2);
//				}
//			}
//		}
//	};
	
}
