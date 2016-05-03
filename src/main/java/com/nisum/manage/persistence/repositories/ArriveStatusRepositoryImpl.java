package com.nisum.manage.persistence.repositories;

import com.nisum.manage.persistence.ArriveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dpinto on 22-04-2016.
 */
@Repository
public class ArriveStatusRepositoryImpl implements ArriveStatusRepository {

    private static Logger logger= Logger.getLogger(ArriveStatusRepositoryImpl.class.toString());

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<ArriveStatus> findAllToSendMail() {

        Date now = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String stringNow=dateFormat.format(now);

        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date to=null;
        Date from = null;

        try {
            to=dateFormat.parse(stringNow);
            from = dateFormat.parse(stringNow);

            Query query = new Query();
            Criteria c=Criteria.where("punctuality").is(Boolean.valueOf(false));
            query.addCriteria(Criteria.where("date").gte(from).lte(to));
            query.addCriteria(c);

             return mongoTemplate.find(query, ArriveStatus.class);

        } catch (ParseException e) {

            logger.log(Level.SEVERE,"A parse exception occurred verify that the Date format is yyyy-MM-dd :",e);
        }  catch(Exception e){
            logger.log(Level.SEVERE,"An exception occurred:",e);
        }
        return null;
    }
    /////////////////////////////////////////////////////////////

    public List<ArriveStatus> findAll(String f, String t, String email) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date to = null;
        Date from = null;

        List<ArriveStatus> basicDBObjects =null;

        try {
            to = dateFormat.parse(t);
            from = dateFormat.parse(f);

            Query query = new Query();
            Criteria c=Criteria.where("email").regex(email);
            query.addCriteria(Criteria.where("date").gte(from).lte(to));
            query.addCriteria(c);

            basicDBObjects= mongoTemplate.find(query, ArriveStatus.class);

        } catch (ParseException e) {

            logger.log(Level.SEVERE,"A parse exception occurred verify that the Date format is yyyy-MM-dd :",e);
        } catch(Exception e){
            logger.log(Level.SEVERE,"An exception occurred:",e);
        }
        return basicDBObjects;
    }

    @Override
    public List<ArriveStatus> findAll() {
        try{
                return mongoTemplate.findAll(ArriveStatus.class,"arriveStatus");
        } catch(Exception e){
            logger.log(Level.SEVERE,"An exception occurred:",e);
        }
        return null;
    }

    @Override
    public void deleteForPurge() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date previousMonth = cal.getTime();

        Query query = new Query();
        query.addCriteria(Criteria.where("date").lte(previousMonth));
        try {

             mongoTemplate.remove(query, ArriveStatus.class);

        } catch(Exception e){
                logger.log(Level.SEVERE,"An exception occurred:",e);
        }

    }

    @Override
    public void save(ArriveStatus entity) {
       try {
            mongoTemplate.save((Object) entity);
       }  catch(Exception e){
            logger.log(Level.SEVERE,"An exception occurred:",e);
       }
    }
}
