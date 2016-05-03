package com.nisum.manage.service.scheduler;

import com.nisum.manage.service.ArriveStatusServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Logger;

/**
 * Created by dpinto on 27-04-2016.
 */
public class PurgeSchedulerServiceImpl implements PurgeSchedulerService {

    private static Logger logger = Logger.getLogger(PurgeSchedulerServiceImpl.class.toString());

    @Autowired
    private ArriveStatusServices asServices;



    @Override
    @Scheduled(fixedDelayString = "${purge.fixedDelay.in.milliseconds}")
    public void serviceMethodToPurge() {
        logger.info("Executing Method scheduled .. .. IT WILL DELETE THE DATA OLDER THAN A MONTH ");

        asServices.deleteForPurge();

    }
}
