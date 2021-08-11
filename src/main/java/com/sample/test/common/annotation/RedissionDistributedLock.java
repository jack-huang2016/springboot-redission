/**
 * FileName: RedissionDistributedLock
 * Author:   huang.yj
 * Date:     2021/8/9 10:58
 * Description: Redission分布式锁
 */
package com.sample.test.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 〈基于注解的Redission分布式锁〉
 *
 * @author huang.yj
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedissionDistributedLock {
    /**
     * 锁的名称
     */
    String value() default "redisson";

    /**
     * 获取锁的等待时间
     */
    int waitTime() default 3;

    /**
     * 锁的过期时间
     */
    int leaseTime() default 13;

    /**
     * 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}