package org.kin.kafka.multithread.configcenter.codec;

import org.kin.kafka.multithread.configcenter.utils.YAMLUtils;
import java.util.Properties;

/**
 * Created by huangjianqin on 2017/9/11.
 */
public class YamlTest {
    public static void main(String[] args){
        Properties properties = YAMLUtils.loadYML2Properties(YamlTest.class.getResource("/").getPath() + "configcenter.yml");
        for(Object key: properties.keySet()){
            System.out.println(key + ">>" + properties.get(key));
        }
    }
}
