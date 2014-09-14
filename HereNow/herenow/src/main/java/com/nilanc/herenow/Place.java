package com.nilanc.herenow;

import com.google.api.client.util.Key;

/**
 * Created by DanNi on 9/13/14.
 */
public class Place {

    @Key
    public String id;

    @Key
    public String name;

    @Key
    public String reference;

    @Key
    public String[] types;

    @Key
    public Event[] events;

    public static class Event {
        @Key
        public String summary;
    }

    @Override
    public String toString() {
        String type ="";
        for(int i=0;i<types.length;i++){
            type+=types[i]+ (i==types.length-1 ? "" : ", ");
        }
        return "\nName: "+name + " - " + " Id: "  +id + " - Reference " + reference + " Types: "+ type;
    }
}
