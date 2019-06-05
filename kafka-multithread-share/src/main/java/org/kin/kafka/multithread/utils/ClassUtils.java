package org.kin.kafka.multithread.utils;

import java.lang.reflect.Field;

/**
 * Created by 健勤 on 2017/7/21.
 */
public class ClassUtils {
    /**
     * 通过无参构造器实例化类
     */
    public static <T> T instance(Class<T> claxx){
        if(claxx == null){
            return null;
        }
        try {
            return claxx.newInstance();
        } catch (InstantiationException e) {
            ExceptionUtils.log(e);
        } catch (IllegalAccessException e) {
            ExceptionUtils.log(e);
        }
        return null;
    }

    public static Object instance(String classStr){
        if(classStr == null || classStr.equals("")){
            return null;
        }
        try {
            Class claxx = Class.forName(classStr);
            return claxx.newInstance();
        } catch (ClassNotFoundException e) {
            ExceptionUtils.log(e);
        } catch (IllegalAccessException e) {
            ExceptionUtils.log(e);
        } catch (InstantiationException e) {
            ExceptionUtils.log(e);
        }
        return null;
    }

    /**
     * 根据参数调用构造器实例化类
     */
    public static <T> T instance(Class<T> claxx, Object... args){
        if(claxx == null){
            return null;
        }
        try {
            T target =  claxx.newInstance();
            for(Object o: args){
                Class oc = o.getClass();
                Class tmp = claxx;
                outer:
                while(tmp != null){
                    for(Field field: tmp.getDeclaredFields()){
                        if(field.getType().isAssignableFrom(oc)){
                            field.setAccessible(true);
                            field.set(target, o);
                            break outer;
                        }
                    }
                    tmp = tmp.getSuperclass();
                }
            }
            return target;
        } catch (InstantiationException e) {
            ExceptionUtils.log(e);
        } catch (IllegalAccessException e) {
            ExceptionUtils.log(e);
        }
        return null;
    }

    public static Class getClass(String className){
        if(className == null || className.equals("")){
            return null;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            ExceptionUtils.log(e);
        }
        return null;
    }
}
