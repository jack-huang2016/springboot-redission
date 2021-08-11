/**
 * FileName: RedisLockAspect
 * Author:   huang.yj
 * Date:     2021/8/6 23:08
 * Description: Redission分布式锁切面
 */
package com.sample.test.common.aop;

import com.sample.test.common.annotation.RedissionDistributedLock;
import com.sample.test.common.util.RedissLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 〈Redission分布式锁切面〉
 *
 * @author huang.yj
 * @create 2021/8/6
 * @since 1.0.0
 */
@Component
@Aspect
@Slf4j
public class RedisLockAspect {

    @Pointcut("@annotation(com.sample.test.common.annotation.RedissionDistributedLock)")
    public void lockAspect() {
    }

    @Around("lockAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        log.info("[开始]执行RedisLock环绕通知,获取Redis分布式锁开始");
        boolean res=false;
        Object obj = null;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissionDistributedLock resDisLock = method.getDeclaredAnnotation(RedissionDistributedLock.class);
        int leaseTime = resDisLock.leaseTime();
        int waitTime = resDisLock.waitTime();
        TimeUnit unit = resDisLock.unit();
        String lockName = resDisLock.value();

        try {
            res = RedissLockUtil.tryLock(lockName, unit, waitTime, leaseTime);
            if(res){
                log.info("获取Redis分布式锁[成功]，加锁完成，开始执行业务逻辑...");
                obj = joinPoint.proceed();
            }
        } catch (Throwable e) {
            log.error("获取Redis分布式锁[异常]，加锁失败，异常信息为：" + e.getMessage(), e);
            throw new RuntimeException();
        } finally{
            if(res){//释放锁
                RedissLockUtil.unlock(lockName);
            }
        }

        log.info("释放Redis分布式锁[成功]，解锁完成，结束业务逻辑...");
        return obj;
    }
}