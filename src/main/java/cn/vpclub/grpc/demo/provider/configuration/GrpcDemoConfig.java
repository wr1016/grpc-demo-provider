package cn.vpclub.grpc.demo.provider.configuration;

import org.springframework.stereotype.Component;

/**
 * 系统常量配置
 * Created by WangRui on 2017/9/21.
 */
@Component

public class GrpcDemoConfig {
    private GrpcDemoConfig() {
    }

    /**
     * 应用服务名称
     */
    public static final String APP_NAME = "mobileOffice";

    /**
     * hazelcast instance name
     */
    public static final String H_INSTANCE_NAME = "hazelcastInstance";

    /**
     * hazelcast 数据库存全局数据锁的名称[应用名:stockLock:目标]
     */
    public static final String H_APP_STOCK_LOCK_NAME = APP_NAME + ":stockLock:all";

    /**
     * hazelcast 数据库存目标数据锁的名称前缀[应用名:stockLock:目标数据(数据ID等)]
     */
    public static final String H_STOCK_LOCK_PREFIX = APP_NAME + ":stockLock:";

    /**
     * hazelcast 缓存队列的名称前缀[应用名:queue:目标对象]
     */
    public static final String H_QUEUE_PREFIX = APP_NAME + ":queue:";

    /**
     * hazelcast IdGenerator的命名空间前缀[应用名:数据表映射对象名]
     */
    public static final String H_ID_GENERATOR_PREFIX = APP_NAME + ":";

    /**
     * hazelcast CacheMap的命名空间前缀[应用名:cache:数据表映射对象名]
     */
    public static final String H_CACHE_PREFIX = APP_NAME + ":cache:";
}
