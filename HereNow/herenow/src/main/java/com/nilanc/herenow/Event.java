package com.nilanc.herenow;

import com.google.api.client.util.Key;

/**
 * Created by DanNi on 9/13/14.
 */
public class Event {
    @Key
    public String event_id;

    @Key
    public String scope;

    @Key
    public String summary;

    @Key
    public String url;
}
