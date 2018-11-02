package com.example.dellpc.khabri.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.dellpc.khabri.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utility
{
    /**
     * Purpose: internet checking
     */
    public static boolean isOnline(Context mContext)
    {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        // test for connection for WIFI
        if (info != null && info.isAvailable() && info.isConnected()) {
            return true;
        }

        info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // test for connection for Mobile
        return info != null && info.isAvailable() && info.isConnected();
    }

    /*Purpose: Wifi is connected but is data connection ok?*/
    public static Boolean isDataAvailable() {
        try {
            Process p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            return (returnVal==0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public static ProgressDialog showLoadingDialog(Context context)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        progressDialog.setContentView(R.layout.meterial_progressview);
        progressDialog.setIndeterminate(true);
        progressDialog.invalidateOptionsMenu();
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        return progressDialog;
    }
}
