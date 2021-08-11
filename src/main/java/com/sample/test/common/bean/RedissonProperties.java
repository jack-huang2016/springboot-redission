/**
 * FileName: RedissonProperties
 * Author:   huang.yj
 * Date:     2021/8/6 22:50
 * Description: redisson属性装配类
 */
package com.sample.test.common.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 〈redisson属性装配类〉
 *
 * @author huang.yj
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {
    private int timeout = 3000;

    private String address;

    private String password;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize=10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;
}