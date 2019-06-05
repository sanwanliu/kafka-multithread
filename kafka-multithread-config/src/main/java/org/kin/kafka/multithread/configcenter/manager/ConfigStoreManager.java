package org.kin.kafka.multithread.configcenter.manager;

import org.kin.kafka.multithread.protocol.app.ApplicationContextInfo;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by huangjianqin on 2017/9/11.
 * 存储系统中统一使用key-value的properties形式进行存储,主要是为了做到格式无关,但是要保证配置写入原子性
 * 而admin可以上传或下载配置,期间就涉及格式转换
 */
public interface ConfigStoreManager{
    void setup(Properties config);
    void clearup();
    boolean isCanStoreConfig(ApplicationContextInfo applicationContextInfo);
    boolean storeConfig(Properties appConfig);
    boolean realStoreConfig(ApplicationContextInfo applicationContextInfo);
    boolean delTmpConfig(ApplicationContextInfo applicationContextInfo);
    Map<String, String> getAppConfigMap(ApplicationContextInfo applicationContextInfo);
    List<Properties> getAllAppConfig(ApplicationContextInfo appHost);
    List<Properties> getAllTmpAppConfig(ApplicationContextInfo appHost);
}
