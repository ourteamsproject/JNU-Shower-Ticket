package com.sevenpounds.shower.job;

import java.io.IOException;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class TicketJob {
    int mid = 1124;

    public TicketJob() {
    }

    public static void main(String[] args) throws Exception {
    }

    @Scheduled(cron = "00 00 10 * * ?")
    public void increat() throws IOException {
        this.mid += 32;
        System.out.println("-------------------------mid 增加");
    }
//    @Scheduled(cron = "00 10 17 * * ?")
//    public void cancelOrder() throws Exception {
//        Operation.cancel(this.mid);
//        System.out.println(new Date()+"取消成功...........");
//        log.debug(new Date()+"取消成功...........");
//    }
    @Scheduled(cron = "00 59 11 * * ?")
    public void makeOrder() throws Exception {
        Long start = System.currentTimeMillis();
        String mess = Operation.order1(this.mid);
        Long end = System.currentTimeMillis();
        if("ok".equals(mess)){
            log.debug(new Date()+" 抢票成功......耗时"+(end-start));
            System.out.println(new Date()+" 抢票失败......耗时"+(end-start));
        }else{
            log.debug(new Date()+" 抢票成......耗时"+(end-start));
            System.out.println(new Date()+" 抢票成......耗时"+(end-start));
        }

    }

    @Scheduled(cron = "0 */30 * * * ?")
    public void log() throws Exception {
        log.debug(new Date()+" 等待中..........mid为："+mid);
        System.out.println("等待中..........mid为："+mid);
    }

//    @Scheduled(cron = "0/5 * * * * ? ")
//    public void test() throws Exception {
//        if(mid!=1089) {
//            System.out.println("结束任务........："+mid);
//            return;
//        }
//        System.out.println("等待中..........mid为："+mid);
//    }

}

