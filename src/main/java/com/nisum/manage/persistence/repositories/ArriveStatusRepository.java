package com.nisum.manage.persistence.repositories;

import com.nisum.manage.persistence.ArriveStatus;

import java.util.List;

/**
 * Created by dpinto on 21-04-2016.
 */

public interface ArriveStatusRepository  {

    public List<ArriveStatus> findAll(String from, String to, String email);

    public List<ArriveStatus> findAllToSendMail();

    List<ArriveStatus> findAll();

    public void  deleteForPurge();

    public void save(ArriveStatus entity);
}
