package com.ubestkid.kidrhymes.utils;

import java.util.List;

/**
 * @author huqinghan/19044311
 * @description $
 * @date 2020/3/19
 */
public class ListUtil {
    public static void addAll(List to, List from){
        for (Object item:from) {
            to.add(item);
        }
    }

    public static <T> boolean isEmpty(List<T> list){
        if (list == null || list.size() == 0){
            return true;
        }
        return false;
    }
}
