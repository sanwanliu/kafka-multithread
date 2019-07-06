package org.kin.kafka.multithread.configcenter;

import org.junit.Before;
import org.kin.kafka.multithread.config.AppConfig;
import org.kin.kafka.multithread.configcenter.utils.JsonUtil;
import org.kin.kafka.multithread.configcenter.utils.PropertiesUtil;
import org.kin.kafka.multithread.configcenter.utils.YAMLUtil;
import org.kin.kafka.multithread.utils.HostUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by huangjianqin on 2017/10/16.
 */
public class TestConfigBase {
    protected Properties properties;

    @Before
    public void init() throws IOException {
        //appConfig.properties
        properties = new Properties();
        String path = TestDiamondRestClient.class.getResource("/").getPath() + "appConfig.properties";
        properties.load(new FileInputStream(new File(path)));
        properties.setProperty(AppConfig.APPHOST, HostUtil.localhost());
    }

    public static String getPropertiesStr(Properties properties){
        StringBuilder sb = new StringBuilder();
        for(Object key: properties.keySet()){
            sb.append(key.toString() + "=" + properties.get(key).toString() + System.lineSeparator());
        }

        return sb.toString();
    }

    public static String getJSONConfig(Properties properties){
        return JsonUtil.map2Json(PropertiesUtil.properties2Map(properties));
    }

    public static String getYAMLConfig(Properties properties){
        return YAMLUtil.transfer2YamlStr(properties);
    }

}
