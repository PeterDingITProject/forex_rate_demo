package com.example.demo.schedule;

import com.example.demo.service.ForexRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ForexRateScheduler {

    Logger logger = LoggerFactory.getLogger(ForexRateScheduler.class);

    @Autowired
    private ForexRateService forexRateService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0 18 * * ?")
    public void fetchForexRatesBatch() {

        logger.info("fetchForexRatesBatch Scheduler StartTime : {}", dateFormat.format(new Date()));

        try {
            forexRateService.fetchAndSaveForexRates();
        } catch (Exception e){
            logger.info("fetchForexRatesBatch Scheduler Error : ", e);
        }

        logger.info("fetchForexRatesBatch Scheduler EndTime : {}", dateFormat.format(new Date()));
    }
}
