package org.kin.kafka.multithread.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Map;

/**
 * Created by huangjianqin on 2017/6/22.
 * TopicPartition的字符表示工具类
 */
public class TPStrUtils {
    /**
     * 生成topicX-partitionX,topicX-partitionX,......字符串
     */
    public static String topicPartitionsStr(Collection<TopicPartition> topicPartitions){
        if(topicPartitions != null && topicPartitions.size() > 0){
            StringBuilder sb = new StringBuilder();
            TopicPartition[] topicPartitionsArr = new TopicPartition[topicPartitions.size()];
            topicPartitions.toArray(topicPartitionsArr);
            sb.append(topicPartitionsArr[0].topic()).append("-").append(topicPartitionsArr[0].partition());
            for(int i = 1; i < topicPartitionsArr.length; i++){
                sb.append(", ").append(topicPartitionsArr[i].topic()).append("-").append(topicPartitionsArr[i].partition());
            }
            return sb.toString();
        }

        return null;
    }

    /**
     * 生成topicX-partitionX(Offset),topicX-partitionX(Offset),......字符串
     */
    public static String topicPartitionOffsetsStr(Map<TopicPartition, OffsetAndMetadata> offsets){
       if(offsets != null && offsets.size() > 0){
           StringBuilder sb = new StringBuilder();
           Map.Entry<TopicPartition, OffsetAndMetadata>[] offsetEntryArr = new Map.Entry[offsets.size()];
           offsets.entrySet().toArray(offsetEntryArr);
           String topic = offsetEntryArr[0].getKey().topic();
           int partition = offsetEntryArr[0].getKey().partition();
           long offset = offsetEntryArr[0].getValue().offset();
           sb.append(topic).append("-").append(partition).append("(").append(offset).append(")");
           for(int i = 1; i < offsetEntryArr.length; i++){
               topic = offsetEntryArr[i].getKey().topic();
               partition = offsetEntryArr[i].getKey().partition();
               offset = offsetEntryArr[i].getValue().offset();
               sb.append(", ").append(topic).append("-").append(partition).append("(").append(offset).append(")");
           }

           return sb.toString();
       }
        
        return null;
    }

    /**
     * 生成ConsumerRecord的具体信息字符串
     */
    public static String consumerRecordDetail(ConsumerRecord record){
        String topic = record.topic();
        int partition = record.partition();
        Object key = record.key();
        Object value = record.value();
        return key + " >>> " + value + "(" + topic + "-" + partition + ")";
    }

}
