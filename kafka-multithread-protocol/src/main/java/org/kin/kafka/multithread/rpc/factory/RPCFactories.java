package org.kin.kafka.multithread.rpc.factory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.kin.kafka.multithread.rpc.factory.impl.DefaultRPCFactoryImpl;
import org.kin.kafka.multithread.utils.ClassUtils;
import org.kin.kafka.multithread.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * Created by huangjianqin on 2017/9/8.
 */
public class RPCFactories{
    private static final Logger log = LoggerFactory.getLogger(RPCFactories.class);
    private static final String DEFAULT_RPCFACTORY = DefaultRPCFactoryImpl.class.getName();
    private static Cache<String, RPCFactory> rpcFactories = CacheBuilder.newBuilder().softValues().build();

    private RPCFactories(){
    }

    public static RPCFactory instance(){
        return instance(DEFAULT_RPCFACTORY);
    }

    public static RPCFactory instance(final String factoryClass){
        RPCFactory rpcFactory = null;
        try {
            rpcFactory =  rpcFactories.get(factoryClass, () -> {
                String iFactoryClass = factoryClass;
                if(iFactoryClass == null || iFactoryClass.equals("")){
                    iFactoryClass = DEFAULT_RPCFACTORY;
                }
                log.info("init RPCFactory(class = " + iFactoryClass + ")");
                RPCFactory factory = (RPCFactory) ClassUtils.instance(iFactoryClass);

                rpcFactories.put(iFactoryClass, factory);
                return factory;
            });
        } catch (ExecutionException e) {
            log.error("hit exception when initting RPCFactory(class = " + factoryClass + ")");
            ExceptionUtils.log(e);
        }
        return rpcFactory;
    }
}
