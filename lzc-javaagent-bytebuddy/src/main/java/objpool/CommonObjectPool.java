package objpool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author: 悟心
 * @time: 2021/11/9 14:16
 * @description:
 */
public class CommonObjectPool extends GenericObjectPool<Student> {

    public CommonObjectPool(PooledObjectFactory<Student> factory, GenericObjectPoolConfig config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }

    public CommonObjectPool(PooledObjectFactory<Student> factory, GenericObjectPoolConfig<Student> config) {
        super(factory, config);

    }
}