package com.nilanc.herenow;

import java.util.Date;

/**
 * Created by Alex on 9/13/14.
 */
public class Message {

    private String msg;
    private boolean self;

    public Message(String msg, boolean self) {
        this.msg = msg;
        this.self = self;
    }

    public String getMsg() {
        return msg;
    }

    public boolean fromSelf() {
        return self;
    }
}
