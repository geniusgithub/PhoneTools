package com.geniusgithub.phonetools.reflect;



import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectClass {
//    private String clazzName = null;
    private Class<?> clazz = null;
//    private Map<String, Method> methodsCache = null;

    protected Object realObject = null;

    public void setRealObject(Object real) {
        realObject = real;
    }

    public Object getRealObject() {
        return realObject;
    }

    public boolean isNull() {
        return realObject == null;
    }

    public ReflectClass(String fullClassName) {
        try {
            clazz = Class.forName(fullClassName);
        } catch (Exception e) {

//            e.printStackTrace();
        }
//        methodsCache = new HashMap<String, Method>();
    }

    public Class getClazz(){
    	return clazz;
    }
    public boolean loaded() {
        return clazz != null;
    }

    public Constructor<?> getConstructor(Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ReflectMethod findStaticMethod(String methodName, Class<?>... paramTypes) {
        ReflectMethod reflectMethod = null;
        
        try {
        	if(!loaded()){
        		return null;
        	}
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
        
            method.setAccessible(true);
            reflectMethod = new ReflectMethod(this, method);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
        	if(clazz!=null){
        		
        	}
        }
        
        return reflectMethod;
    }
    
    public ReflectMethod findInstanceMethod(String methodName, Class<?>... paramTypes) {
        ReflectMethod reflectMethod = null;
        
        try {
        	if(!loaded()){
        		return null;
        	}
            Method method = clazz.getMethod(methodName, paramTypes);
        
            method.setAccessible(true);
            reflectMethod = new ReflectMethod(this, method);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
        	if(clazz!=null){
        		
        	}
        }
        
        return reflectMethod;
    }
    
    public ReflectConstructor findConstructor(Class<?>... paramTypes) {
        ReflectConstructor reflectConstructor = null;
        try {
            Constructor<?> con = clazz.getDeclaredConstructor(paramTypes);
            con.setAccessible(true);
            reflectConstructor = new ReflectConstructor(this, con);
        } catch (Exception e) {

            //e.printStackTrace();
        }
        return reflectConstructor;
    }

    public int getConstInt(String constFieldName) {

        Field constField;
        try {
            constField = clazz.getField(constFieldName);
            constField.setAccessible(true);
            return constField.getInt(null);
        } catch (SecurityException e) {
      
        } catch (NoSuchFieldException e) {
        
        } catch (IllegalArgumentException e) {
        
        } catch (IllegalAccessException e) {
        
        }
        return 0;
    }

    public void setIntFieldValue(String fieldName,int value ,Object instance){
        Field field;
        try {
            field = clazz.getField(fieldName);
            field.setAccessible(true);
            field.setInt(instance, value);
        } catch (SecurityException e) {
        
        } catch (NoSuchFieldException e) {
        
        } catch (IllegalArgumentException e) {
       
        } catch (IllegalAccessException e) {
        	
        }            
    }
    
    public Object getConstObject(String constFieldName) {
        Field constField;
        try {
            constField = clazz.getField(constFieldName);
            constField.setAccessible(true);
            return constField.get(constFieldName);
        } catch (SecurityException e) {
        } catch (NoSuchFieldException e) {
        
        } catch (IllegalArgumentException e) {
      
        } catch (IllegalAccessException e) {
        
        }
        return null;
    }
    
    public boolean getFieldBoolean(String constFieldName) {
        Field constField;
        try {
            constField = clazz.getField(constFieldName);
            constField.setAccessible(true);
            return constField.getBoolean(realObject);
        } catch (SecurityException e) {
        } catch (NoSuchFieldException e) {
     
        } catch (IllegalArgumentException e) {
        
        } catch (IllegalAccessException e) {
        
        }
        return false;
    }
    
    public String getFieldString(String constFieldName) {
        Field constField;
        try {
            constField = clazz.getField(constFieldName);
            constField.setAccessible(true);
            return (String)constField.get(realObject);
        } catch (SecurityException e) {
        } catch (NoSuchFieldException e) {
 
        } catch (IllegalArgumentException e) {
       
        } catch (IllegalAccessException e) {
        
        }
        return null;
    }
    
    
    
    
    public long getConstLong(String constFieldName) {

        Field constField;
        try {
            constField = clazz.getField(constFieldName);
            constField.setAccessible(true);
            return constField.getLong(null);
        } catch (SecurityException e) {
     
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
        return 0;
    }

    public String getConstString(String constFieldName) {
        Field constField;
        try {
            constField = clazz.getField(constFieldName);
            constField.setAccessible(true);
            return String.valueOf(constField.get(null));
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
  
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean getConstBoolean(String constFieldName) {
        Field constField;
        try {
            constField = clazz.getField(constFieldName);
            constField.setAccessible(true);
            return constField.getBoolean(null);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected <T> T invokeInstanceMethod(String methodName, Class<?>[] paramsClass, Class<T> retClass, Object... param) {
        ReflectMethod rm = findInstanceMethod(methodName, paramsClass);
        if(rm == null) {
        
        	return null;
        }
        rm.setInvokeParams(param);
        return rm.invoke(realObject, retClass);
    }

    protected <T> T invokeStaticMethod(String methodName, Class<?>[] paramsClass, Class<T> retClass, Object... param) {
    	ReflectMethod method = this.findStaticMethod(methodName, paramsClass);
    	return method.setInvokeParams(param).invoke(null, retClass);
    }

    public String toString() {
        return clazz == null ? "null" : "class:" + clazz.getName();
    }
}
