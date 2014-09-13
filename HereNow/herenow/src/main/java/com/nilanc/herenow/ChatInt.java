package com.nilanc.herenow;

/**
 * Created by Alex on 9/13/14.
 */

import org.jivesoftware.smack.XMPPException;

public interface ChatInt {

    void sendMessage(String message) throws XMPPException;

    void release() throws XMPPException;
}
