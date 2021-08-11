/**
 * FileName: ProductKillService
 * Author:   huang.yj
 * Date:     2021/8/6 23:17
 * Description: Redission使用
 */
package com.sample.test.service;

import com.sample.test.common.annotation.RedissionDistributedLock;
import com.sample.test.common.util.RedissLockUtil;
import java.util.concurrent.TimeUnit;

/**
 * 〈Redission使用〉
 *
 * @author huang.yj
 * @since 1.0.0
 */
public class ProductKillService {

    // 1、常规加锁解决超卖现象
    public boolean startProductkillRedisLock(String productKillId) {
        boolean res=false;
        try {
            res = RedissLockUtil.tryLock(productKillId, TimeUnit.SECONDS, 3, 20);
            if(res){
                // 1、根据秒杀的商品ID查询商品对应的库存数量
                // Productkill productKill = seckillService.selectById(productKillId);
                // 2、如果查到的商品的库存大于0的话，则执行减少库存数量的操作
                // if(productKill.getNumber()>0){
                int qty = 10;  // 这里为了案例先写死查到的商品的库存还有10个
                if (qty > 0) {
                    // 3、新增或修改订单状态
                    // 4、连接数据库，更改库存的数据
                }else{
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(res){//释放锁
                RedissLockUtil.unlock(productKillId+"");
            }
        }
        return res;
    }

    // 2、自定义注解加锁解决超卖现象
    @RedissionDistributedLock(value="goods", leaseTime = 10)  // 这里建议增加个EL表达式的功能，获取#productKillId入参
    public boolean startProductkillAnnoLock(String productKillId) {
        boolean res=false;
        try {
            // 1、根据秒杀的商品ID查询商品对应的库存数量
            // Productkill productKill = seckillService.selectById(productKillId);
            // 2、如果查到的商品的库存大于0的话，则执行减少库存数量的操作
            // if(productKill.getNumber()>0){
            int qty = 10;  // 这里为了案例先写死查到的商品的库存还有10个
            if (qty > 0) {
                // 3、新增或修改订单状态
                // 4、连接数据库，更改库存的数据
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}