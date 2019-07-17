package org.kin.kafka.multithread.configcenter.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huangjianqin on 2017/11/2.
 */
public abstract class AbstractConfigStoreManager implements ConfigStoreManager {
    protected static final Logger log = LoggerFactory.getLogger(AbstractConfigStoreManager.class);
}
