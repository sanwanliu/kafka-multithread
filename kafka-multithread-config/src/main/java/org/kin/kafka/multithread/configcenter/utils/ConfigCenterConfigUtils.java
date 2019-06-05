package org.kin.kafka.multithread.configcenter.utils;

import org.kin.kafka.multithread.configcenter.ConfigCenterConfig;
import java.util.*;

/**
 * Created by huangjianqin on 2017/9/12.
 */
public class ConfigCenterConfigUtils {
    public static void fillDefaultConfig(Properties config){
        if(config == null){
            return;
        }
        Properties tmp = deepCopy(ConfigCenterConfig.DEFAULT_CONFIG);
        tmp.putAll(config);
        config.clear();
        config.putAll(tmp);
    }

    public static Properties deepCopy(Properties config){
        if(config == null){
            return null;
        }

        Properties clonedProperties = new Properties();
        for(Map.Entry<Object, Object> entry: config.entrySet()){
            clonedProperties.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return clonedProperties;
    }

    public static boolean isConfigItemChange(Properties lastConfig, Properties newConfig, Object key){
        if(lastConfig == null && newConfig == null){
            throw new IllegalStateException("last properties or new properties state wrong");
        }

        if(lastConfig == null){
            return true;
        }

        if(newConfig == null){
            return false;
        }

        if(lastConfig.containsKey(key)){
            return isConfigItemChange(lastConfig.get(key), newConfig, key);
        }
        else{
            throw new IllegalStateException("last properties or new properties state wrong");
        }
    }

    public static boolean isConfigItemChange(Object lastValue, Properties newConfig, Object key){
        if(newConfig.containsKey(key)){
            if(!lastValue.equals(newConfig.get(key))){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            throw new IllegalStateException("new properties state wrong");
        }
    }

    public static List<Properties> allNecessaryCheckAndFill(List<Properties> newConfigs){
        List<Properties> result = new ArrayList<>();
        for(Properties config: newConfigs){
            oneNecessaryCheckAndFill(config);
            if(config != null){
                result.add(config);
            }
        }
        return result;
    }

    public static void oneNecessaryCheckAndFill(Properties newConfig){
        //填充默认值
        ConfigCenterConfigUtils.fillDefaultConfig(newConfig);
        //检查配置格式
        if(!checkConfigValueFormat(newConfig)){
            return;
        }
    }

    public static boolean checkConfigValueFormat(Properties config){
        for(Map.Entry<String, String> entry: ConfigCenterConfig.CONFIG2FORMATOR.entrySet()){
            //如果=默认值,则不管格式问题
            String value = config.getProperty(entry.getKey());
            if(!value.equals(ConfigCenterConfig.DEFAULT_CONFIG.get(entry.getKey())) &&
                    !config.getProperty(entry.getKey()).matches(value)){
                throw new IllegalStateException("config \"" +  entry.getKey() + "\" 's value \"" + entry.getValue() + "\" format is not correct");
            }
        }
        return true;
    }
    public static String toString(Properties config){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Object, Object> entry: config.entrySet()){
            sb.append(entry.getKey()).append("  =  ").append(entry.getValue()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
