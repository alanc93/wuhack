package com.nilanc.herenow;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by DanNi on 9/13/14.
 */
public class PlacesList {
    @Key
    public String status;

    @Key
    public List<Place> results;
}
