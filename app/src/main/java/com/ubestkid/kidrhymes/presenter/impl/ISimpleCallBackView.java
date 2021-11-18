package com.ubestkid.kidrhymes.presenter.impl;

/**
 * @Des 回调
 * @Date 2019/12/8
 * @Author huqinghan
 */
public interface ISimpleCallBackView<T>{
    void success(T bean);
    void error(String msg);
}
