package com.ubestkid.kidrhymes.utils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @Des
 * @Date 2020/5/9
 * @Author huqinghan
 */
public class BeanUtils {
    /**
     * 方法说明：将bean转化为另一种bean实体
     *
     * @param object
     * @param entityClass
     * @return
     */
    public static <T> T convertBean(Object object, Class<T> entityClass) {
        if(null == object) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(object), entityClass);
    }

    /**
     * 方法说明：对象转化为Map
     *
     * @param object
     * @return
     */
    public static Map<?, ?> objectToMap(Object object){
        return convertBean(object, Map.class);
    }
}
