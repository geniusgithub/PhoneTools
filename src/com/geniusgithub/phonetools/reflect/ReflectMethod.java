package com.geniusgithub.phonetools.reflect;



import java.lang.reflect.Method;


public class ReflectMethod {
//    private ReflectClass methodOwner = null;
    private Method realMethod =null;
    private Object[] methodInvokeParams = null;
    
    public ReflectMethod(ReflectClass owner, Method method){
//        methodOwner =owner;
        realMethod = method;
    }
    
    public ReflectMethod setInvokeParams(Object... params){
        methodInvokeParams = params;
        return this;
    }
    
    public <T> T invoke(Object intance, Class<T> retType) {
        try {
            if (realMethod != null) {
       
                if (methodInvokeParams == null) {
                    return (T) realMethod.invoke(intance);
                } else {
                    return (T) realMethod.invoke(intance, methodInvokeParams);
                }
            }
        } catch (Exception e) {
      
            e.printStackTrace();
        }
        return (T) null;
    }
}
