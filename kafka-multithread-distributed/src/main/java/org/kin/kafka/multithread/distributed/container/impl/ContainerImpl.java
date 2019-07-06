package org.kin.kafka.multithread.distributed.container.impl;

import org.kin.kafka.multithread.distributed.container.Container;
import org.kin.kafka.multithread.distributed.node.ContainerContext;
import org.kin.kafka.multithread.distributed.node.NodeContext;
import org.kin.kafka.multithread.protocol.distributed.ContainerMasterProtocol;
import org.kin.kafka.multithread.protocol.distributed.NodeMasterProtocol;
import org.kin.kafka.multithread.rpc.factory.RPCFactories;
import org.kin.kafka.multithread.utils.HostUtil;

/**
 * Created by huangjianqin on 2017/9/18.
 */
public class ContainerImpl extends Container {
    private boolean isStopped = false;

    public ContainerImpl(ContainerContext containerContext, NodeContext nodeContext) {
        super(containerContext, nodeContext);
    }

    @Override
    public void doStart() {
        //启动RPC接口
        RPCFactories.instance().serviceWithoutRegistry(ContainerMasterProtocol.class, this, containerMasterProtocolPort);
        RPCFactories.instance().clientWithoutRegistry(NodeMasterProtocol.class, HostUtil.localhost(), nodeMasterProtocolPort);

        while(isStopped){
            //阻塞main线程......
            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doClose() {
        isStopped = true;
    }
}
