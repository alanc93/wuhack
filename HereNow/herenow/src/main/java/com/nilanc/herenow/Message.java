package com.nilanc.herenow;

import java.util.Date;

/**
 * Created by Alex on 9/13/14.
 */
public class Message {

    private String msg;
    private Date time;

    public Message(String msg, Date time) {
        this.msg = msg;
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public Date getTime() {
        return time;
    }
}
