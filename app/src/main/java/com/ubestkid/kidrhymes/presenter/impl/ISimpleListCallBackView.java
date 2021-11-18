package com.ubestkid.kidrhymes.presenter.impl;

import java.util.List;

/**
 * @Des 简单列表回调
 * @Date 2019/12/8
 * @Author huqinghan
 */
public interface ISimpleListCallBackView<T>{
    void success(List<T> list);
    void error(String msg);
}
