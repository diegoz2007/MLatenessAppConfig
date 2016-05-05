package com.nisum.manage.service.scheduler;

import com.nisum.manage.persistence.ArriveStatus;
import com.nisum.manage.service.ArriveStatusServices;
import com.nisum.manage.util.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dpinto on 26-04-2016.
 */
public class NotificationSchedulerServiceImpl implements NotificationSchedulerService {

    private static Logger logger= Logger.getLogger(NotificationSchedulerServiceImpl.class.toString());

    @Value("${mail.notification.sender}")
    private String senderEmail;

    @Value("${mail.message}")
    private String messageToSend;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private ArriveStatusServices asServices;

    @Override
    @Scheduled(fixedDelayString = "${mail.notification.fixedDelay.in.milliseconds}")
    public void serviceMethodToNotification() {

        logger.info("Executing Method scheduled .. .. IT WILL SEND AN EMAIL TO THE EMPLOYEE WITH LATENEES.");

        Date d=new Date();


            List<ArriveStatus> basicDBObjects = asServices.findAllToSendMail();

            if(basicDBObjects==null || basicDBObjects.size()==0 )
                   return;


        emailUtils.sendMail(senderEmail,recipientList(basicDBObjects),"Please do not be late!",messageToSend);

    }

    private String[] recipientList(List<ArriveStatus> basicDBObjects){
        String [] recipients=new String[basicDBObjects.size()];
        int i=0;
        for(ArriveStatus as:basicDBObjects) {
            recipients[i] = as.getEmail();
            i++;
        }
        return recipients;
    }

}
