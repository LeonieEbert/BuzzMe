package com.example.buzzme;

import java.util.Date;

public class Timestamp {
    private Date start;
    private Date stop;


    public Timestamp(Date start) {
        this.start = start;
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
