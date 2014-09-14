package com.nilanc.herenow;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;

import org.apache.http.client.HttpResponseException;

import java.io.IOException;

/**
 * Created by DanNi on 9/13/14.
 */
public class LocationSearch {
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";

    private static final String API_KEY = "AIzaSyA0H2gmjFao_osUOe6ZXJqZDTUO6iOuwEQ";

    private static final HttpTransport transport = new ApacheHttpTransport();

    public static HttpRequestFactory createRequestFactory(
            final HttpTransport transport) {

        return transport.createRequestFactory(new HttpRequestInitializer() {

            @Override
            public void initialize(com.google.api.client.http.HttpRequest request) throws IOException {
                HttpHeaders headers = new HttpHeaders();
                headers.setUserAgent("HereNow");
                request.setHeaders(headers);
                JsonObjectParser parser = new JsonObjectParser(new JacksonFactory());
                request.setParser(parser);
            }
        });
    }

    public PlacesList performSearch() throws Exception{
        double lat = LocListener.latitude;
        double lon = LocListener.longitude;
        System.out.println(lat);
        System.out.println(lon);
        int radius = 150;

        return performSearch(lat, lon, radius);
    }

    public PlacesList performSearch(double lat, double lon, int radius) throws Exception {
        try {
            System.out.println("Perform Search ....");
            System.out.println("-------------------");
            HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
            com.google.api.client.http.HttpRequest request = httpRequestFactory
                    .buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
            request.getUrl().put("key", API_KEY);
            request.getUrl().put(
                    "location",
                    lat + ","
                            + lon);
            request.getUrl().put("radius", radius);


            PlacesList places = request.execute().parseAs(PlacesList.class);
            System.out.println("STATUS = " + places.status);
//            for (Place place : places.results) {
//                System.out.print(place.name);
//
//            }
            return places;

        } catch (HttpResponseException e) {
            System.out.println("error");
            throw e;
        }
    }

}
