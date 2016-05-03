package com.nisum.manage.controller;


import com.nisum.manage.persistence.ArriveStatus;
import com.nisum.manage.service.ArriveStatusServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dpinto on 21-04-2016.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/app")
public class RestController {

    private static Logger logger=Logger.getLogger(RestController.class.toString());


    @Autowired
    private ArriveStatusServices asServices;


    @RequestMapping(value = "/search/since={since}&until={until}/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<List<ArriveStatus>> listAllArriveStatus(@PathVariable("since") String since, @PathVariable("until") String until, @PathVariable("email") String email) {
        List<ArriveStatus> arriveStatus= null;

        logger.info("Starting searching for  Status Entry from Date:"+since+" to Date:"+until+" with employee email: " + email);

            arriveStatus = asServices.findAll(since,until,email);



        if(arriveStatus.isEmpty()){
            logger.info("List returned is empty --HttpStatus: "+HttpStatus.NO_CONTENT);
            return new ResponseEntity<List<ArriveStatus>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        logger.info("List returned  --HttpStatus: "+HttpStatus.OK);
        return new ResponseEntity<List<ArriveStatus>>(arriveStatus, HttpStatus.OK);
    }

    ////////////////////////////////////////////////////
    @RequestMapping(value = "/entry", method = RequestMethod.POST)
    public ResponseEntity<Void> insertArriveStatus(@RequestBody ArriveStatus arriveStatus,    UriComponentsBuilder ucBuilder) {

        logger.info("Creating Status Entry for the employee: " + arriveStatus.getEmail());


        asServices.save(arriveStatus);


        HttpHeaders headers = new HttpHeaders();
        logger.info("The Status Entry has been created --HttpStatus: "+HttpStatus.CREATED);
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

    }


}
