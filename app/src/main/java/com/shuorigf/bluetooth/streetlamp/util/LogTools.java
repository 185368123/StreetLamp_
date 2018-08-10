package com.shuorigf.bluetooth.streetlamp.util;


import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

public class LogTools {

	public static boolean isDebug = true;
	public static boolean isWrite = true;
	
    private static String m_path;
    
    private static final String INFORMAT = "yyyy-MM-dd HH:mm:ss";  
	
	
	public static void e(String tag, String msg) {
		if (isDebug) {
			Log.e(tag, msg);
		}		
		
		if(isWrite)
		{
			write(tag, msg);  
		}
	}
	
	public static void w(String tag, String msg) {
		if (isDebug) {
			Log.w(tag, msg);
		}
		
		if(isWrite)
		{
			 write(tag, msg);  
		}
	}
	
	public static void i(String tag, String msg) {
		if (isDebug) {
			Log.i(tag, msg);
		}
		
		if(isWrite)
		{
			 write(tag, msg);  
		}
	}
	
	public static void d(String tag, String msg) {
		if (isDebug) {
			Log.d(tag, msg);
		}
		
		if(isWrite)
		{
			 write(tag, msg);  
		}
	}
	
	public static void v(String tag, String msg) {
		if (isDebug) {
			Log.v(tag, msg);
		}
		
		if(isWrite)
		{
			 write(tag, msg);  
		}
	}
	
	
	/** 
     * 用于把日志内容写入制定的文件 
     *  
     * @param @param tag 标识 
     * @param @param msg 要输出的内容 
     * @return void 返回类型 
     * @throws 
     */  
    private static void write(String tag, String msg) {  
    	  if (TextUtils.isEmpty(m_path)) {  
	            return;
	        } 
        String log = DateFormat.format(INFORMAT, System.currentTimeMillis())  
                + "["+ tag  
                + "]:["  
                + msg + "]" 
                + "\n";
        FileIOUtils.writeFileFromString(m_path, log, true);
    }  


	 public static void setDebugFile(String path)
		{
		 m_path = path;
		}

	 public static void delDebugFile()
		{
		 FileUtils.deleteFile(m_path);
		}
}


