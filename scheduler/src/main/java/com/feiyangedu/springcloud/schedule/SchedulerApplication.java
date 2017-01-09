package com.feiyangedu.springcloud.schedule;

import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Spring Boot Application.
 * 
 * @author Michael Liao
 */
@SpringBootApplication
@EnableScheduling
public class SchedulerApplication {

	Log log = LogFactory.getLog(getClass());

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SchedulerApplication.class, args);
	}

	/**
	 * 以固定10秒的频率执行
	 */
	@Scheduled(fixedRate = 10_000, initialDelay = 6_000)
	public void doFixedRateTask() throws Exception {
		log.info("[FIXED RATE] scheduled at " + LocalDateTime.now().toString());
		Thread.sleep(2000);
	}

	/**
	 * 以固定10秒的间隔执行（上一个任务结束后等待10秒）
	 */
	@Scheduled(fixedDelay = 10_000, initialDelay = 7_000)
	public void doFixedDelayTask() throws Exception {
		log.info("[FIXED DELAY] scheduled at " + LocalDateTime.now().toString());
		Thread.sleep(2000);
	}

	/**
	 * 周一至周五，**:**:03执行
	 */
	@Scheduled(cron = "3 * * * * MON-FRI")
	public void doCronTask() throws Exception {
		log.info("[CRON] scheduled at " + LocalDateTime.now().toString());
	}
}
