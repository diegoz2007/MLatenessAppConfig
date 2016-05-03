package com.nisum.manage.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dpinto on 21-04-2016.
 */
@Document(collection="arriveStatus")
public class ArriveStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String entryId;

    private Boolean punctuality;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    private String email;

    public ArriveStatus() {}

    public ArriveStatus(Boolean punctuality, Date date, String email) {

        this.punctuality=punctuality;
        this.date=date;
        this.email=email;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(Boolean punctuality) {
        this.punctuality = punctuality;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
}
