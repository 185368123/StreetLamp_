package com.shuorigf.bluetooth.streetlamp.util;

import android.Manifest;

/**
 * Created by clx on 2017/12/7.
 */

public class Constants {
    public static final String USER_ACCOUNT = "roadsmart";
    public static final String USER_PASSWORD = "Rs23281185";
    public static boolean IS_LOGIN = false;
    public static final long SEND_DATA_TIME_OVER = 3000;

    public static final String[] Y_YEAR = {"2014", "2015", "2016", "2017", "2018"};
    public static final String[] Y_MONTH = {"01", "03", "05", "07", "09", "11"};
    public static final String[] Y_DAY = {"01", "04", "07", "11", "14", "17", "21", "24", "27"};

    public static final String MAC = "MAC";
    public static final String DEVICE_INFO = "device_info";
    public static final String SOLAR_PANEL_INFO = "solar_panel_info";
    public static final String BATTERY_INFO = "battery_info";
    public static final String LOAD_INFO = "load_info";
    public static final String WORK_MODE_FIRST_SECOND_INFO = "work_mode_first_second_info";
    public static final String WORK_MODE_CONTENT_INFO = "work_mode_content_info";
    public static final String SET_DATA_UNIT = "set_data_unit";
    public static final String SET_DATA_TITLE= "set_data_title";
    public static final String SET_DATA_VALUE= "set_data_value";
    public static final String SWITCH_BUTTON_TITLE= "switch_button_title";
    public static final String SWITCH_BUTTON_VALUE= "switch_button_value";

    public static final String TYPE_HISTORICAL_DATA_CONTENT = "type_historical_data_content";

    public static final int TYPE_DEVICE_TESTING = 100;
    public static final int TYPE_DEVICE_PRODUCT = 101;
    public static final int TYPE_DEVICE_VERSION = 102;
    public static final int TYPE_RESTORE_FACTORY_SETTINGS = 103;

    public static final int TYPE_SOLAR_PANEL = 104;
    public static final int TYPE_BATTERY = 105;
    public static final int TYPE_LOAD = 106;

    public static final int TYPE_L_MODE_FIRST_SECOND = 107;
    public static final int TYPE_T_MODE_FIRST_SECOND = 108;
    public static final int TYPE_M_MODE_FIRST_SECOND = 109;
    public static final int TYPE_U_MODE_FIRST_SECOND = 110;
    public static final int TYPE_OVER = 111;
    public static final int TYPE_WORK_MODE_CONTENT = 112;
    public static final int TYPE_HISTORICAL_DATA = 113;
    public static final int TYPE_BATTERY_TEMPERATURE = 114;
    public static final int TYPE_SET_COLOR_LIGHT_SOURCE = 115;
    public static final int TYPE_SET_SMART_POWER= 116;
    public static final int TYPE_SET_INDUCTION_DISTANCE = 117;
    public static final int TYPE_SET_TEMPERATURE_PROTECTION = 118;
    public static final int TYPE_SET_MUSIC_SWITCH= 119;
    public static final int TYPE_HISTORICAL_CHART_DATA = 120;
    public static final int TYPE_SET_LIGHT_CONTROL_VOLTAGE = 121;
    public static final int TYPE_SET_LIGHT_CONTROL_DELAY =  122;
    public static final int TYPE_SET_LOAD_CURRENT =  123;
    public static final int TYPE_SET_OVER_CHARGE_VOLTAGE =  124;
    public static final int TYPE_SET_OVER_CHARGE_RETURN =  125;
    public static final int TYPE_SET_OVER_DISCHARGE_RETURN =  126;
    public static final int TYPE_SET_OVER_DISCHARGE_VOLTAGE =  127;
    public static final int TYPE_HISTORICAL_CHART_DATA_NON_ADMIN = 128;
    public static final int TYPE_HISTORICAL_CHART_DATA_NON_ADMIN_END = 129;
    public static final int TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC = 130;
    public static final int TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC_END = 131;
    public static final int TYPE_HISTORICAL_CHART_DATA_ADMIN_MANAGER = 132;
    public static final int TYPE_HISTORICAL_CHART_DATA_ADMIN_MANAGER_END = 133;

    public final static int TYPE_SET_L_FIRST_TIME=134;
    public final static int TYPE_SET_L_FIRST_POWER=135;
    public final static int TYPE_SET_L_FIRST_COLOR_TEMPERATURE=136;
    public final static int TYPE_SET_L_SECOND_TIME=137;
    public final static int TYPE_SET_L_SECOND_POWER=138;
    public final static int TYPE_SET_L_SECOND_COLOR_TEMPERATURE=139;
    public final static int TYPE_SET_L_THREE_TIME=140;
    public final static int TYPE_SET_L_THREE_POWER=141;
    public final static int TYPE_SET_L_THREE_COLOR_TEMPERATURE=142;
    public final static int TYPE_SET_L_FOUR_TIME=143;
    public final static int TYPE_SET_L_FOUR_POWER=144;
    public final static int TYPE_SET_L_FOUR_COLOR_TEMPERATURE=145;

    public final static int TYPE_SET_T_FIRST_TIME=146;
    public final static int TYPE_SET_T_FIRST_POWER=147;
    public final static int TYPE_SET_T_FIRST_COLOR_TEMPERATURE=148;
    public final static int TYPE_SET_T_SECOND_TIME=149;
    public final static int TYPE_SET_T_SECOND_POWER=150;
    public final static int TYPE_SET_T_SECOND_COLOR_TEMPERATURE=151;
    public final static int TYPE_SET_T_THREE_TIME=152;
    public final static int TYPE_SET_T_THREE_POWER=153;
    public final static int TYPE_SET_T_THREE_COLOR_TEMPERATURE=154;
    public final static int TYPE_SET_T_MORNING_TIME=155;
    public final static int TYPE_SET_T_MORNING_POWER=156;
    public final static int TYPE_SET_T_MORNING_COLOR_TEMPERATURE=157;


    public final static int TYPE_SET_M_FIRST_TIME=158;
    public final static int TYPE_SET_M_FIRST_POWER=159;
    public final static int TYPE_SET_M_FIRST_COLOR_TEMPERATURE=160;
    public final static int TYPE_SET_M_SECOND_TIME=161;
    public final static int TYPE_SET_M_HAVE_ONE_POWER=162;
    public final static int TYPE_SET_M_HAVE_ONE_COLOR_TEMPERATURE=163;
    public final static int TYPE_SET_M_INDUCTION_DELAY=164;
    public final static int TYPE_SET_M_NO_ONE_POWER=165;
    public final static int TYPE_SET_M_HAVE_TWO_POWER=166;
    public final static int TYPE_SET_M_HAVE_TWO_COLOR_TEMPERATURE=167;
    public final static int TYPE_SET_M_NO_TWO_POWER=168;
    public final static int TYPE_SET_M_NO_PEOPLE_COLOR_TEMPERATURE=169;

    public final static int TYPE_SET_U_FIRST_TIME=170;
    public final static int TYPE_SET_U_FIRST_POWER=171;
    public final static int TYPE_SET_U_FIRST_COLOR_TEMPERATURE=172;
    public final static int TYPE_SET_U_SECOND_TIME=173;
    public final static int TYPE_SET_U_SECOND_POWER=174;
    public final static int TYPE_SET_U_SECOND_COLOR_TEMPERATURE=175;
    public final static int TYPE_SET_U_THREE_TIME=176;
    public final static int TYPE_SET_U_THREE_POWER=177;
    public final static int TYPE_SET_U_THREE_COLOR_TEMPERATURE=178;
    public final static int TYPE_SET_U_INDUCTION_DELAY=179;
    public final static int TYPE_SET_U_NO_PEOPLE_POWER=180;
    public final static int TYPE_SET_U_NO_PEOPLE_COLOR_TEMPERATURE=181;

    public static final int TYPE_SOLAR_PANEL_STATUS = 182;
    public static final int TYPE_BATTERY_STATUS = 183;
    public static final int TYPE_LOAD_STATUS = 184;

    public static final int TYPE_LAMP_BRIGHTNESS = 185;
    public static final int TYPE_SET_LAMP_BRIGHTNESS = 186;

    public static final int TYPE_READ_MODE = 187;
    public final static int TYPE_SET_MODE=188;

    //intent key
    public static final String POWER_CHART_TYPE = "power_chart_type";

    //request code key
    public static final int REQUEST_CODE_LOGIN = 201;


    public static final int DATE_TYPE_ALL = 301;
    public static final int DATE_TYPE_YEAR = 302;
    public static final int DATE_TYPE_MONTH = 303;

    public static final String[] LOCATION_PERM = {Manifest.permission.ACCESS_COARSE_LOCATION};
    public static final int REQUEST_CODE_LOCATION_PERM = 900;//定位权限
}
