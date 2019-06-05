package org.kin.kafka.multithread.protocol.distributed;

import org.kin.kafka.multithread.domain.ConfigResultRequest;
import org.kin.kafka.multithread.domain.HealthReport;

/**
 * Created by huangjianqin on 2017/9/18.
 * 向node更新Container自己的状态(health,监控==)
 * 告诉Node关闭Container
 */
public interface NodeMasterProtocol {
    void report(HealthReport report);
    void commitConfigResultRequest(ConfigResultRequest configResultRequest);
    void containerClosed(long containerId);
}
