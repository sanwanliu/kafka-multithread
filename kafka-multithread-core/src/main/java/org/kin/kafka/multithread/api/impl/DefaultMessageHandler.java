package org.kin.kafka.multithread.api.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.kin.kafka.multithread.api.MessageHandler;
import org.kin.kafka.multithread.statistics.Counters;

import java.util.Properties;

/**
 * Created by hjq on 2017/6/21.
 * 默认的Message handler
 * 仅仅添加相应的计数器
 */
public class DefaultMessageHandler implements MessageHandler<String, String> {

    @Override
    public void setup(Properties config) throws Exception {

    }

    @Override
    public void handle(ConsumerRecord<String, String> record) throws Exception {
        Counters.getCounters().add("consumer-counter");
        Counters.getCounters().add("consumer-byte-counter", record.value().getBytes().length);
    }

    @Override
    public void cleanup() throws Exception {

    }
}
