package com.nilanc.herenow;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.List;

/**
 * Created by DanNi on 9/13/14.
 */
public class LocListener implements LocationListener {

    public static double latitude;
    public static double longitude;

    public void onLocationChanged(Location loc)
    {
        latitude=loc.getLatitude();
        longitude=loc.getLongitude();

        Main.updateUI();
    }

    public void onProviderDisabled(String provider)
    {
        Main.updateUI();
    }
    public void onProviderEnabled(String provider)
    {
        Main.updateUI();
    }
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }

    public static Location getLocation(Context ctx) {
        LocationManager lm = (LocationManager) ctx
                .getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        Location l = null;
        for (int i = providers.size() - 1; i >= 0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null)
                break;
        }
        latitude = l.getLatitude();
        longitude = l.getLongitude();
        return l;
    }
}
