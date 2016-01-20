package com.geniusgithub.phonetools.reflect;

import java.lang.reflect.Constructor;

public class ReflectConstructor {
//    private ReflectClass methodOwner = null;
    private Constructor<?> realConstructor =null;
    private Object[] methodInvokeParams = null;
    
    public ReflectConstructor(ReflectClass owner,Constructor<?> method){
//        methodOwner =owner;
        realConstructor = method;
    }
    
    public ReflectConstructor setInvokeParams(Object... params){
        methodInvokeParams = params;
        return this;
    }
    
    public Object _new() {
        try {
            if (realConstructor != null) {
                return  realConstructor.newInstance(methodInvokeParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }    
}
