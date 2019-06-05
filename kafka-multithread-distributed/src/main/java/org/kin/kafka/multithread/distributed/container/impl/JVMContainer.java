package org.kin.kafka.multithread.distributed.container.impl;

import org.kin.kafka.multithread.distributed.container.Container;
import org.kin.kafka.multithread.distributed.node.ContainerContext;
import org.kin.kafka.multithread.distributed.node.NodeContext;
import org.kin.kafka.multithread.protocol.distributed.ContainerMasterProtocol;
import org.kin.kafka.multithread.protocol.distributed.NodeMasterProtocol;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huangjianqin on 2017/9/18.
 *
 */
public class JVMContainer extends Container {
    public JVMContainer(ContainerContext containerContext, NodeContext nodeContext, NodeMasterProtocol nodeMasterProtocol) {
        super(containerContext, nodeContext);
        super.nodeMasterProtocol = nodeMasterProtocol;
    }

    @Override
    public void doStart() {
    }

    @Override
    public void doClose() {
    }
}
