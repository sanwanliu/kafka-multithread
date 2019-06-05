package org.kin.kafka.multithread;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.kin.kafka.multithread.domain.ApplicationContext;
import org.kin.kafka.multithread.api.MultiThreadConsumerManager;
import org.kin.kafka.multithread.api.impl.DefaultCommitStrategy;
import org.kin.kafka.multithread.utils.AppConfigUtils;
import org.kin.kafka.multithread.common.PropertiesWrapper;
import org.kin.kafka.multithread.statistics.Counters;
import org.kin.kafka.multithread.api.impl.RealEnvironmentMessageHandler;
import org.kin.kafka.multithread.config.AppConfig;
import org.kin.kafka.multithread.config.DefaultAppConfig;
import org.kin.kafka.multithread.statistics.Statistics;

import java.util.*;

/**
 * Created by hjq on 2017/6/22.
 */
public class TestMultiThreadConsumerManager {
    public static void main(String[] args) throws InterruptedException {
        long startTime = 0;
        long endTime = 0;

        Properties config = PropertiesWrapper.create()
                .set(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "ubuntu:9092")
                .set(ConsumerConfig.GROUP_ID_CONFIG, "multi-handle")
                .set(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
                .set(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
                .set(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
                .set(AppConfig.APPNAME, "test")
                .set(AppConfig.MESSAGEHANDLERMANAGER_MODEL, DefaultAppConfig.OCOT)
                .set(AppConfig.KAFKA_CONSUMER_SUBSCRIBE, "msg")
                .set(AppConfig.MESSAGEHANDLER, RealEnvironmentMessageHandler.class.getName())
                .set(AppConfig.COMMITSTRATEGY, DefaultCommitStrategy.class.getName())
                .set(AppConfig.CONSUMERREBALANCELISTENER, "org.kin.kafka.multithread.api.impl.SimpleConsumerRebalanceListener")
                .properties();

        //1.一个consumer
        //改pendingwindow的配置
        config.setProperty(AppConfig.PENDINGWINDOW_SLIDINGWINDOW, "10");
        config.setProperty(AppConfig.OCOT_CONSUMERNUM, "1");
        ApplicationContext context = MultiThreadConsumerManager.instance().newApplication(config);
        context.start();
        startTime = System.currentTimeMillis();

        long runTime = 2 * 60 * 1000;
        long lastConsumerCounter = -1;

        long temp = Counters.getCounters().get("consumer-counter");
        while(temp <= 100 && (System.currentTimeMillis() - startTime < runTime)){
            temp = Counters.getCounters().get("consumer-counter");
        }
        //测试更新配置
        Properties newConfig = AppConfigUtils.deepCopy(config);
        //改messagefetcher配置
//        newConfig.setProperty(AppConfig.MESSAGEFETCHER_POLL_TIMEOUT, "30000");
        //改pendingwindow的配置
//        newConfig.setProperty(AppConfig.PENDINGWINDOW_SLIDINGWINDOW, "10");
        //改OPMT配置
//        newConfig.setProperty(AppConfig.OPMT_HANDLERSIZE, "15");

//        newConfig.setProperty(AppConfig.OPMT_MINTHREADSIZEPERPARTITION, "10");
//        newConfig.setProperty(AppConfig.OPMT_MAXTHREADSIZEPERPARTITION, "10");
        //改OPMT2配置
//        newConfig.setProperty(AppConfig.OPMT2_THREADSIZEPERPARTITION, "5");
        //改OCOT配置
        newConfig.setProperty(AppConfig.OCOT_CONSUMERNUM, "2");

        context.reConfig(newConfig);
        //重复多少次相同消费记录则退出
        int sameCounter = 5;
        while(System.currentTimeMillis() - startTime < runTime){
            //下面逻辑会出现sameCounter+1个相同的消费记录
            if(lastConsumerCounter < Counters.getCounters().get("consumer-counter")){
                lastConsumerCounter = Counters.getCounters().get("consumer-counter");
                sameCounter = 5;
            }
            else if(lastConsumerCounter == Counters.getCounters().get("consumer-counter")){
                sameCounter --;
                if(sameCounter <= 0){
                    break;
                }
            }
            if(lastConsumerCounter == 1000000){
                break;
            }
            System.out.println("当前消费:" + lastConsumerCounter + "条");
            System.out.println("当前消费:" + Counters.getCounters().get("consumer-byte-counter") + "字节");
            Thread.sleep(60 * 1000);
        }
        endTime = System.currentTimeMillis();
        if(sameCounter <= 0){
            //重复出现多次重复的消费记录,要减去这部分的等待时间
            endTime -= 5 * 60 * 1000;
        }

        context.close();

        long sum = Counters.getCounters().get("consumer-counter");
        long sum1 = Counters.getCounters().get("consumer-byte-counter");
        System.out.println("总消费:" + sum + "条");
        System.out.println("总消费:" + sum1 + "字节");
        System.out.println("总消费率: " + 1.0 * sum / (endTime - startTime) + "条/ms");
        System.out.println("总消费率: " + 1.0 * sum1 / (endTime - startTime) + "B/ms");
        System.out.println("总耗时:" + (endTime - startTime));
        System.out.println(Statistics.instance().get("offset"));
    }
}
