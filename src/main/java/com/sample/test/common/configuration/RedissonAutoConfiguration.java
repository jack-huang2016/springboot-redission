/**
 * FileName: RedissonAutoConfiguration
 * Author:   huang.yj
 * Date:     2021/8/6 23:00
 * Description: Redisson自动装配类
 */
package com.sample.test.common.configuration;

import com.sample.test.common.bean.GlobalConstant;
import com.sample.test.common.bean.RedissonProperties;
import com.sample.test.common.util.RedissLockUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈Redisson自动装配类〉
 *
 * @author huang.yj
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Autowired
    private RedissonProperties redssionProperties;

    /**
     * 单机模式自动装配
     * @return
     */
    @Bean(name = {"redissonClient"})
    @ConditionalOnProperty(name="single.redisson")
    public RedissonClient redissonSingle() {
        // 构造redisson实现分布式锁必要的Config
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + redssionProperties.getAddress())
                .setTimeout(redssionProperties.getTimeout())
                 .setDatabase(0)  // 选取下标为0的数据库
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());

        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }

        return Redisson.create(config);
    }

    /**
     * 哨兵模式自动装配
     * @return
     */
/*   @Bean(name = {"redissonClient"})
   @ConditionalOnProperty(name="sentinel.redisson")
   public RedissonClient redissonSentinel() {
       // 构造redisson实现分布式锁必要的Config
       Config config = new Config();
       SentinelServersConfig serverConfig = config.useSentinelServers()
               .setMasterName(redssionProperties.getMasterName())
               .setTimeout(redssionProperties.getTimeout())
               .setDatabase(0) // 选取下标为0的数据库
               .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
               .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());

       if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
           serverConfig.setPassword(redssionProperties.getPassword());
       }

       String[] addrArr = redssionProperties.getAddress().split(",");
       for (int i = 1; i < addrArr.length; i++) {
           serverConfig.addSentinelAddress(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + addrArr[i]);
       }

       return Redisson.create(config);
   }*/

    /**
     * 集群模式自动装配
     * @return
     */
/*    @Bean(name = {"redissonClient"})
    @ConditionalOnProperty(name="cluster.redisson")
    public RedissonClient redissonCluster() {
        // 构造redisson实现分布式锁必要的Config
        Config config = new Config();

        ClusterServersConfig serverConfig = config.useClusterServers()
                .setTimeout(redssionProperties.getTimeout())
                .setScanInterval(5000)
                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());

        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }

        String[] addrArr = redssionProperties.getAddress().split(",");
        for (int i = 0; i < addrArr.length; i++) {
            serverConfig.addNodeAddress(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + addrArr[i]);
        }

        return Redisson.create(config);
    }*/

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"redissonClient"})
    RedissLockUtil redissLockUtil(RedissonClient redissonClient) {
        RedissLockUtil redissLockUtil = new RedissLockUtil();
        redissLockUtil.setRedissonClient(redissonClient);
        return redissLockUtil;
    }
}