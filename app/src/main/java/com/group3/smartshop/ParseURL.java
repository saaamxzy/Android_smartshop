package com.group3.smartshop;

/**
 * Created by Zhengyuan Xu on 2016/10/30.
 */

import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.group3.smartshop.R.string.yelp_token;


public class ParseURL extends AsyncTask<URL, Void, String> {


    @Override
    protected String doInBackground(URL... params){
        testCall();
        return "";
    }


    public static void testCall() {



        String testURL =
                "https://api.yelp.com/v3/autocomplete?text=del&latitude=37.786882&longitude=-122.399972";
        try {

            URL url = new URL(testURL);
            String bearerAuth = "Bearer " + yelp_token;
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setRequestProperty("Authorization", bearerAuth);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");





            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();

        } catch (MalformedURLException e){
            System.out.println("Malformed URL: " + e.getMessage());
        } catch (IOException e){
            System.out.println("IO exception: " + e.getMessage());
        }
    }


}
