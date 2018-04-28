package com.example.buzzme;

import java.sql.Time;
import java.util.Date;

public class Timestamp {
    private String timestampId;
    private Date start;
    private Date stop;


    public Timestamp(String id ,Date start){
        setTimestampId(id);
        setStart(start);
        setStop(start);
    }




    public String getTimestampId() {
        return timestampId;
    }
    public void setTimestampId(String timestampId) {
        this.timestampId = timestampId;
    }



    public Timestamp(Date start) {
        this.start = start;
    }
    public Timestamp(){}
    public Timestamp (String id,Date start, Date stop){
        this.timestampId= id;
        this.start=start;
        this.stop=stop;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

}
