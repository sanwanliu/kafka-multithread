package org.kin.kafka.multithread.configcenter.codec;

import java.util.Map;

/**
 * Created by huangjianqin on 2017/9/11.
 */
public interface StoreCodec {
    /**
     * 序列化成key-value的properties形式
     */
    Map<String, String> deSerialize(String source);

    /**
     * 合并元数据与配置
     */
    Map<String, String> merge(String sourceConfig, String appName, String host);

    /**
     * 将key-value的properties形式反序列化成对应格式(json, yaml,properties)
     */
    String serialize(Map<String, String> serialized);

    /**
     * 校验格式(json, yaml,properties)是否合法
     */
    boolean vertify(String configStr);
}
