package com.group3.smartshop;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;

public class OnlineSearchActivity extends AppCompatActivity {
    private String message;
    ArrayList<String> list = new ArrayList<String>();

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_search);
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new ProductAdapter(this, productList);

        RecyclerView.LayoutManager myLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(myLayout);
        recyclerView.addItemDecoration(new SpacingItemDecoration(2,convert(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        try{
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.bg_back));
        }catch (Exception e){
            e.printStackTrace();
        }

        new doit().execute();
    }

    private int convert (int x){
        Resources source = getResources();
        return Math.round(TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, x, source.getDisplayMetrics()));
    }


    public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int count;
        private int space;
        private boolean hasEdge;

        public SpacingItemDecoration (int count, int space, boolean hasEdge){
            this.count = count;
            this.space = space;
            this.hasEdge = hasEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % count;

            if (hasEdge == true){
                outRect.left = space - column * space/count;
                outRect.right = (column + 1) * space/count;

                if (position < count){
                    outRect.top = space;
                }
                outRect.bottom = space;
            }else{
                outRect.left = column * space/count;
                outRect.right = space - (column + 1) * space/count;

                if(position >= count){
                    outRect.top = space;
                }
            }
        }
    }

    public void refresh(){
        adapter.notifyDataSetChanged();
    }

    public class doit extends AsyncTask<Void,Void,Void> {

        private ProductAdapter adapter;
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


            // TODO: is there any way to put image into here??
            int[] pictures = new int[]{
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart,
                    R.drawable.shopping_cart};

            for(int i = 1; i<=10; i++) {
                String nameText = "";
                String textPrice = "";
                double price = 0.00;

                int index = list.get(i).indexOf('$');
                if (index != -1) {
                    nameText = list.get(i).substring(0, index);
                } else {
                    nameText = "No matching product!";
                }

                textPrice = list.get(i).substring(index + 2);
                int indexSpace1 = textPrice.indexOf(' ');

                if (textPrice.substring(0, indexSpace1).indexOf('.')!= -1){
                    int pindex = textPrice.substring(0, indexSpace1).indexOf('.');
                    price = Double.parseDouble(textPrice.substring(0, pindex+3));
                }else {
                    String temp = textPrice.substring(0, indexSpace1+3);
                    temp = temp.replace(' ', '.');
                    price = Double.parseDouble(temp);
                }

                Product temp = new Product(nameText, price, pictures[i]);
                productList.add(temp);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            });
            return null;
        }


//        @Override
//        protected void onPostExecute(Void aVoid){
//            LinearLayout screen = (LinearLayout)findViewById(R.id.main_search_onlilne);
//            screen.setOrientation(LinearLayout.VERTICAL);
//            super.onPostExecute(aVoid);
//            for(int i = 0; i<list.size(); i++)
//            {
//                TextView newView = new TextView(OnlineSearchActivity.this);
//                newView.setText(list.get(i));
//                screen.addView(newView);
//            }
//
//        }
    }
}
