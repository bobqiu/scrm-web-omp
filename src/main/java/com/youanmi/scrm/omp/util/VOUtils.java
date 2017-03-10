/*
 * 文件名：VOUtils.java
 * 版权：Copyright 2015 youanmi Tech. Co. Ltd. All Rights Reserved. 
 * 描述： VOUtils.java
 * 修改人：龙汀
 * 修改时间：2015年4月1日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;


/**
 * VO工具类
 * 
 * @author 龙汀
 * @version YouAnMi-OTO 2015年4月1日
 * @since YouAnMi-OTO
 */
public class VOUtils {

    private static ObjectMapper objectMapper = null;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
        // true);
        // objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    }

    /** 分页默认大小 **/
    private static int PAGE_DEFAULT_SIZE;


    /**
     * 
     * 设置默认的分页大小
     * 
     * @param size
     *            分页大小
     */
    public static void setDefaultPageSize(int size) {
        PAGE_DEFAULT_SIZE = size;
    }


    /**
     * 
     * 获取默认的分页大小
     * 
     * @return 分页大小
     */
    public static int getDefaultPageSize() {
        return PAGE_DEFAULT_SIZE;
    }


    /**
     * 
     * 处理对象输出带换行空格格式的JSON字符串，用于记录日志。
     * 
     * { "code" : "", "desc" : "" }
     * 
     * @param o
     * @return
     */
    public static String stringify(Object o) {
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentArraysWith(new DefaultIndenter());
        try {
            return objectMapper.writer(printer).writeValueAsString(o);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 
     * 对象转JSON
     * 
     * @param value
     * @return
     */
    public static String toJSON(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 
     * JSON转对象
     * 
     * @param json
     * @param type
     * @return
     */
    public static <T> T toObj(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
