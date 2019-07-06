package org.kin.kafka.multithread.configcenter;

import org.junit.Test;
import org.kin.kafka.multithread.domain.ConfigFetcherHeartbeatRequest;
import org.kin.kafka.multithread.protocol.app.ApplicationContextInfo;
import org.kin.kafka.multithread.protocol.configcenter.DiamondMasterProtocol;
import org.kin.kafka.multithread.rpc.factory.RPCFactories;
import org.kin.kafka.multithread.utils.HostUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by huangjianqin on 2017/10/17.
 */
public class TestDiamondRPCClient extends TestConfigBase{

    public DiamondMasterProtocol client(){
        return RPCFactories.instance().clientWithoutRegistry(DiamondMasterProtocol.class, HostUtil.localhost(), 60001);
    }

    @Test
    public void heartbeat(){
        ConfigFetcherHeartbeatRequest heartbeat = new ConfigFetcherHeartbeatRequest(
                new ApplicationContextInfo("test1", HostUtil.localhost()),
                Collections.singletonList(new ApplicationContextInfo("test1", HostUtil.localhost())),
                new ArrayList<>(),
                System.currentTimeMillis()
        );
        DiamondMasterProtocol client = client();
        client.heartbeat(heartbeat);
    }
}
