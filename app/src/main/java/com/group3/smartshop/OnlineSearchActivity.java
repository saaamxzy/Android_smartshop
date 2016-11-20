package com.group3.smartshop;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> pics = new ArrayList<String>();

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_search);
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        initToolbar();

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

    public Double getPrice (String price){
        double correctPrice = 0;
        try
        {
            correctPrice = Double.parseDouble(price.replaceAll(",", ""));
        }
        catch(Exception e)
        {
            String subPrice = price.substring(0,price.indexOf('.'));
            correctPrice = Double.parseDouble(subPrice.replaceAll(",",""));
        }
        return correctPrice;
    }

    private void initToolbar() {
        final CollapsingToolbarLayout toolbar
                = (CollapsingToolbarLayout)findViewById(R.id.app_toolbar);
        toolbar.setTitle(" ");
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.app_bar_1);
        appBar.setExpanded(true);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout layout, int offset) {
                if (scrollRange == -1) {
                    scrollRange = layout.getTotalScrollRange();
                }
                if (scrollRange + offset == 0) {
                    toolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public void refresh(){
        adapter.notifyDataSetChanged();
    }

    public class doit extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String website = "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords="+message;

            list.clear();
            pics.clear();

            try {

                Document doc = Jsoup.connect(website).get();
                Elements result = doc.select("li[id^=result_]");

                for(Element i: result) {
                    String word = i.text();
                    list.add(word);
                }

                for (Element i : result.select("img")) {
                    String p = i.attr("src");
                    System.out.println("all: " + p);
                    if (p.contains("_AC_US160_.jpg")) {
                        pics.add(p);
                        System.out.println("true");
                    }else{
                        continue;
                    }
                }
            }catch(Exception e){e.printStackTrace();}


//            int[] pictures = new int[]{
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart,
//                    R.drawable.shopping_cart};

            boolean cate = false;

            for(int i = 0; i<10; ++i) {
                String nameText = "";
                String textPrice = "";
                String finalPrice = "";
                double price = 0.00;

                int index = list.get(i).indexOf('$');
                if (index != -1) {
                    nameText = list.get(i).substring(0, index);
                } else {
                    cate = true;
                    continue;
                }

                if (list.get(i).charAt(index+1) == ' ') {
                    textPrice = list.get(i).substring(index + 2);
                }else{
                    textPrice = list.get(i).substring(index + 1);
                }

                for (int j = 0; j < textPrice.length(); ++j){
                    if (textPrice.charAt(j) == '.'){
                        finalPrice = textPrice.substring(0, j+3);
                        break;
                    }

                    if ( textPrice.charAt(j) == ' '){
                        textPrice = textPrice.replace(' ', '.');
                        finalPrice = textPrice.substring(0, j+3);
                        break;
                    }
                }

                if (finalPrice.length() == 0) price = -1;
                else
                {
                    System.out.println("This is final price:\n\n\n" + finalPrice);
                    price = getPrice(finalPrice);
                }


                Product pro;
                if (cate) {
                    pro = new Product(nameText, price, pics.get(i-1));
                }else {
                    pro = new Product(nameText, price, pics.get(i));
                }

                productList.add(pro);
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
