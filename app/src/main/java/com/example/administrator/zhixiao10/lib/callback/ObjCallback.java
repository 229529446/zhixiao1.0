package com.example.administrator.zhixiao10.lib.callback;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public abstract class ObjCallback <T> {

    private final  Class<T> clazz;

    public ObjCallback(){
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    public Class<T> getDataClass(){
        return clazz;
    }

    public abstract  void onSuccess(T data);
    public abstract  void onFailure();
}
