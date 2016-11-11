package com.group3.smartshop;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class OnlineSearchActivity extends AppCompatActivity {
    private String message;
    ArrayList<String> list = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_search);
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        new doit().execute();

    }
    public class doit extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String website = "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords="+message;
            try {

                Document doc = Jsoup.connect(website).get();
                Elements result = doc.select("li[id^=result_]");

                for(Element i: result) {
                    String word = i.text();
                    list.add(word);
                }
            }catch(Exception e){e.printStackTrace();}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            LinearLayout screen = (LinearLayout)findViewById(R.id.activity_online_search);
            screen.setOrientation(LinearLayout.VERTICAL);
            super.onPostExecute(aVoid);
            for(int i = 0; i<list.size(); i++)
            {
                TextView newView = new TextView(OnlineSearchActivity.this);
                newView.setText(list.get(i));
                screen.addView(newView);
            }
        }
    }
}
