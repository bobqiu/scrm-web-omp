/*
 * 文件名：AssertUtils.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 描述： AssertUtils.java
 * 修改人：龙汀
 * 修改时间：2015年9月25日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Collection;


/**
 * 断言类
 * <p>
 * 为空判断
 * 
 * 
 * @author 龙汀
 * @since 2.2.4
 */
public class AssertUtils {
    /**
     * 
     * 字符串为空或者null判断
     * 
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return str == null || str.isEmpty();
    }


    /**
     * 
     * 字符串为空字符串判断
     * 
     * @param str
     * @return true:是空字符串""，false:不为空或者为null
     */
    public static boolean isEmpty(String str) {
        return str != null && str.isEmpty();
    }


    /**
     * 
     * 集合为空判断
     * 
     * @param v
     * @return
     */
    public static boolean isNull(Collection<?> v) {
        return v == null || v.isEmpty();
    }


    /**
     * 字符串不为空判断
     * 
     * 
     * @param str
     * @return
     */
    public static boolean notNull(String str) {
        return !isNull(str);
    }


    /**
     * 
     * 数字为空判断
     * 
     * @param v
     * @return
     */
    public static boolean isNull(Number v) {
        return v == null || v.intValue() == 0;
    }


    /**
     * 
     * BigDecimal为空判断
     * 
     * @param n
     * @return
     */
    public static boolean notNull(BigDecimal v) {
        return v != null && v.compareTo(new BigDecimal(0)) > 0;
    }


    /**
     * 
     * BigDecimal为空判断
     * 
     * @param n
     * @return
     */
    public static boolean isNull(BigDecimal v) {
        return !notNull(v);
    }


    /**
     * 
     * 数字为空判断
     * 
     * @param n
     * @return
     */
    public static boolean notNull(Number n) {
        return !isNull(n);
    }


    /**
     * 
     * 集合不为空判断
     * 
     * @param v
     * @return
     */
    public static boolean notNull(Collection<?> v) {
        return !isNull(v);
    }


    /**
     * 
     * 反射，方法没有参数判断
     * 
     * @param m
     * @return
     */
    public static boolean notPram(Method m) {
        return m.getParameterTypes().length == 0;
    }


    /**
     * 
     * 方法不是静态的判断
     * 
     * @param m
     * @return
     */
    public static boolean notStatic(Method m) {
        int mo = m.getModifiers();
        return !Modifier.isStatic(mo);
    }


    /**
     * 
     * 数组为空判断
     * 
     * @param data
     * @return
     */
    public static boolean isNull(byte[] data) {
        return data == null || data.length == 0;
    }


    public static boolean equalsTrue(String value) {
        return "true".equals(value);
    }
}
