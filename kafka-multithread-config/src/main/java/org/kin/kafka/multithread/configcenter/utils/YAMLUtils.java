package org.kin.kafka.multithread.configcenter.utils;

import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by huangjianqin on 2017/9/11.
 */
public class YAMLUtils {
    /**
     * 多层嵌套map
     */
    public static Map<String, Object> loadYML(String configPath){
        try {
            return (Map<String, Object>) Yaml.load(new File(configPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Properties loadYML2Properties(String configPath){
        Map<String, Object> yaml = loadYML(configPath);
        Properties properties = new Properties();
        transfer2Properties(yaml, properties, "");

        //因为jyaml会识别类型,所有有些数字型数据需要转换为字符型数据
        for(Object key: properties.keySet()){
            properties.put(key, properties.get(key).toString());
        }

        return properties;
    }

    /**
     * 将多层嵌套map转换成A.B.C的properties格式
     */
    private static void transfer2Properties(Map<String, Object> yaml, Properties properties, String keyHead){
        for(String key: yaml.keySet()){
            Object value = yaml.get(key);
            if(value instanceof HashMap){
                transfer2Properties((Map<String, Object>) value, properties, keyHead.equals("")? key : keyHead + "." + key);
            }
            else {
                if(value == null){
                    properties.put(keyHead.equals("")? key : keyHead + "." + key, "");
                }
                else{
                    properties.put(keyHead.equals("")? key : keyHead + "." + key, value);
                }
            }
        }

    }

    /**
     * 将多层嵌套map转换成A.B.C的properties的map格式
     */
    private static void transfer2Map(Map<String, Object> yaml, Map<String, String> map, String keyHead){
        for(String key: yaml.keySet()){
            Object value = yaml.get(key);
            if(value instanceof HashMap){
                transfer2Map((Map<String, Object>) value, map, keyHead.equals("")? key : keyHead + "." + key);
            }
            else {
                if(value == null){
                    map.put(keyHead.equals("")? key : keyHead + "." + key, "");
                }
                else{
                    map.put(keyHead.equals("")? key : keyHead + "." + key, value.toString());
                }
            }
        }
    }

    public static Map<String, String> transfer2Map(Map<String, Object> yaml){
        Map<String, String> config = new HashMap<>();
        transfer2Map(yaml, config, "");
        return config;
    }

    /**
     * 将配置转换为Yaml字符串
     */
    public static String transfer2YamlStr(Map<String, Object> yaml){
        return Yaml.dump(yaml);
    }

    public static String transfer2YamlStr(Properties config){
        return transfer2YamlStr(transfer2Yaml(config));
    }

    /**
     * 将A.B.C的properties的map格式转换多层嵌套map
     */
    private static void transfer2Yaml(Map<String, Object> yaml, Map config){
        for(Object key: config.keySet()){
            String keyStr = (String) key;
            if(keyStr.contains("\\.")){
                String[] split = keyStr.split("\\.", 2);
                Map<String, Object> nextLevel = yaml.containsKey(split[0])? (Map<String, Object>) yaml.get(split[0]) : new HashMap<>();
                if(!yaml.containsKey(split[0])){
                    yaml.put(split[0], nextLevel);
                }
                deepMap(nextLevel, split[1], config.get(key));
            }
            else {
                yaml.put(key.toString(), config.get(key));
            }
        }
    }

    public static Map<String, Object> transfer2Yaml(Map<String, String> config){
        Map<String, Object> yaml = new HashMap<>();
        transfer2Yaml(yaml, config);
        return yaml;
    }

    /**
     * 不断递归创建多层嵌套map
     * 尾递归,提交性能
     * @param key 下面层数的key+.组成
     */
    private static void deepMap(Map<String, Object> nowLevel, String key, Object value){
        if(key.contains("\\.")){
            String[] split = key.split("\\.", 2);
            Map<String, Object> nextLevel = nowLevel.containsKey(split[0])? (Map<String, Object>) nowLevel.get(split[0]) : new HashMap<>();
            if(!nowLevel.containsKey(split[0])){
                nowLevel.put(split[0], nextLevel);
            }
            deepMap(nextLevel, split[1], value);
        }else{
            nowLevel.put(key, value);
        }
    }

    public static Map<String, Object> transfer2Yaml(Properties config){
        Map<String, Object> yaml = new HashMap<>();
        transfer2Yaml(yaml, config);
        return yaml;
    }

}
