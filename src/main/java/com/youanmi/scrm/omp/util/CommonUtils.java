package com.youanmi.scrm.omp.util;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.youanmi.scrm.commons.util.number.SysUniqueIdUtil;
import com.youanmi.scrm.omp.constants.OmpConstants;


/**
 * 公共工具类
 * 
 * @author 龙汀
 *
 */
public class CommonUtils {

    // private static final Logger LOG =
    // LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 系统配置: 根据Key获取配置文件中的值。
     * 
     * @param key
     * @return
     */
    public static String getSysConf(String key) {
        String strValue = SystemConfUtils.getConf(key);
        if (strValue == null) {
            strValue = SystemConfUtils.getConf(key);
        }
        return StringUtils.trim(strValue);
    }
    
    /**
     * 系统配置: 根据Key获取配置文件中的值。
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getSysConf(String key,String defaultValue) {
        String strValue = SystemConfUtils.getConf(key);
        if (strValue == null) {
            strValue = SystemConfUtils.getConf(key);
        }else {
            strValue = defaultValue;
        }
        return StringUtils.trim(strValue);
    }


    /**
     * 获取系统配置,转换成boolean类型
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getSysBooleanConf(String key, Boolean defaultValue) {
        String value = getSysConf(key);
        if (value != null) {
            try {
                return Boolean.valueOf(value).booleanValue();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * 获取系统配置,转换成boolean类型
     * 
     * @param key
     * @return
     */
    public static boolean getSysBooleanConf(String key) {
        String value = getSysConf(key);
        if (value != null) {
            try {
                return Boolean.valueOf(value).booleanValue();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 获取系统配置,转换成Integer类型
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Integer getSysIntegerConf(String key, Integer defaultValue) {
        String value = getSysConf(key);
        if (value != null) {
            try {
                return Integer.valueOf(value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }


    /**
     * 获取系统配置,转换成Float类型
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Float getSysFloatConf(String key, Float defaultValue) {
        String value = getSysConf(key);
        if (value != null) {
            try {
                return Float.valueOf(value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }


    /**
     * 
     * 项目自定义配置：根据Key获取配置文件中的值。
     * 
     * @param key
     * @return
     */
    public static String getCusConf(String key) {
        String strValue = CustomConfUtils.getConf(key);
        if (strValue == null) {
            strValue = CustomConfUtils.getConf(key);
        }
        return StringUtils.trim(strValue);
    }


    /**
     * 
     * 生成验证码。
     * 
     * @param size
     *            验证码长度。
     * @return
     */
    public static String getVerifyCode(int size) {
        StringBuilder strCode = new StringBuilder();
        long lSeed = new Date().getTime();
        Random random = new Random();
        random.setSeed(lSeed);
        for (int i = 0; i < size; i++) {

            strCode.append(random.nextInt(10));
        }
        return strCode.toString();
    }


    /**
     * 
     * 数组转换为以,号分割的字符串。
     * 
     * @param objs
     * @return
     */
    public static String arrayToStr(Object[] objs) {
        if (objs == null) {
            return null;
        }
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objs.length; i++) {
            if (first) {
                first = false;
            }
            else {
                sb.append(OmpConstants.CommonModule.DATA_SEPARATOR);
            }
            sb.append(objs[i]);
        }
        return sb.toString();
    }


    /**
     * 
     * 获取图片名字
     * 
     * @return
     */
    public static String getImgName() {
        return SysUniqueIdUtil.nextId();
    }


    /**
     * 
     * 字符串转list
     * 
     * @param element
     * @return
     */
    public static List<String> string2list(String element) {
        List<String> list = new ArrayList<String>();
        list.add(element);
        return list;

    }


    // public static void main(String[] args) throws ParseException {
    //
    // LOG.info(cutFileType("A.sddd.txtg"));
    // LOG.info("time=" +
    // transferStringDateToLong("2015-04-01 00:00:00"));
    // LOG.info(System.currentTimeMillis());
    // // getAssureNo("");
    //
    // LOG.info(formatDate(1435819843991l, MONTH_DAY));
    // }
    public static String list2String(List<String> list) {
        if (AssertUtils.isNull(list)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(",");
        }
        String str = sb.toString();
        return str.substring(0, str.length() - 1);
    }
    
    /**
     * 字符串转化为list
     * @param str
     * @return
     */
    public static List<String> string2List1(String str) {
    	List<String> result = new ArrayList<String>();
    	if (AssertUtils.isNull(str)) {
    		return result;
    	}
    	for (String s : str.split(",")) {
    		result.add(s);
    	}
    	return result;
    }
}
