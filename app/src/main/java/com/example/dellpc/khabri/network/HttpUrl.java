package com.example.dellpc.khabri.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.dellpc.khabri.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class HttpUrl {
    private static String TAG=HttpUrl.class.getSimpleName();
    public static final String APIKEY = "22721e8220e04f62b1b0f9483d3f33cd";
    public static final String APIKEYPARAM = "apiKey";
    /* The query parameter allows us to provide a location string to the API */
    private static final String QUERY_PARAM = "q";

    private static final String FROM_PARAM = "from";
    private static final String SORT_BY = "sortBy";
    private static final String POPULARITY = "popularity";
    private static final String EVERYTHING = "everything";

    /* The format parameter allows us to designate whether we want JSON or XML from our API */
    private static final String FORMAT_PARAM = "mode";

    /* The format we want our API to return */
    private static final String format = "json";


    public static String getBaseUrl(Context context) {
        return context.getResources().getString(R.string.baseUrl);

    }

    /**
     * Retrieves the proper URL to query for the weather data. The reason for both this method as
     * well as  is two fold.
     * <p>
     * 1) You should be able to just use one method when you need to create the URL within the
     * app instead of calling both methods.
     * 2) Later in Sunshine, you are going to add an alternate method of allowing the user
     * to select their preferred location. Once you do so, there will be another way to form
     * the URL using a latitude and longitude rather than just a location String. This method
     * will "decide" which URL to build and return it.
     *
     * @param context used to access other Utility methods
     * @return URL to query weather service
     */
    public static URL getUrl(Context context, String locationQuery,String fromdate,String toDate) {
            return buildUrlWithLocationQuery(locationQuery,context,fromdate,toDate);
    }



    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param locationQuery The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    private static URL buildUrlWithLocationQuery(String locationQuery,Context ctx,String fromdate,String toDate) {
        Uri weatherQueryUri = Uri.parse(ctx.getResources().getString(R.string.baseqUERY)).buildUpon()
                .appendQueryParameter(QUERY_PARAM, locationQuery)
                .appendQueryParameter(FROM_PARAM, fromdate)
                .appendQueryParameter(SORT_BY, POPULARITY)
                .appendQueryParameter(APIKEYPARAM, APIKEY)
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.v(TAG, "URL: " + weatherQueryUrl);
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}
