package com.group3.smartshop;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.group3.smartshop.MapsActivity.BASE_URL;
import static com.group3.smartshop.MapsActivity.YELP_TOKEN;

/**
 * Created by Isabella on 2016/11/27.
 */

public class Recommendation extends AppCompatActivity {

    private ArrayList<Business> recommendations;
    private List<Business> businesses;

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recommend_main);

        initToolbar();

        recommendations = new ArrayList<>();
        productList = new ArrayList<Product>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_rec);
        adapter = new ProductAdapter(this, productList);

        RecyclerView.LayoutManager myLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(myLayout);
        recyclerView.addItemDecoration(new SpacingItemDecoration(1,convert(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        //Get recommendations nearby

        LatLng myLl = new LatLng(32.8673,-117.209);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        YelpApiEndPointInterface apiService = retrofit.create(YelpApiEndPointInterface.class);
        Call<YelpParser> call =
                apiService.getBusinesses(YELP_TOKEN, "shop", 32.8672972, -117.209346);
        call.enqueue(new Callback<YelpParser>() {
            @Override
            public void onResponse(Call<YelpParser> call, Response<YelpParser> response) {
                if (response.body() == null) {
                    System.out.println("body is null");
                }
                businesses = response.body().getBusinesses();
                Collections.sort(businesses, new BusinessComparator());
                Collections.reverse(businesses);
                recommendations = getRecommendations(businesses, 6);


                for (Business b : recommendations) {
                    System.out.println(b.getName());
                    Product curr = new Product(b.getName(), b.getPrice(),
                            b.getImageUrl(), b.getUrl(), "yelp");
                    productList.add(curr);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<YelpParser> call, Throwable t) {
                System.out.println("businesses not grabbed");
                System.out.println(t.toString());
            }

        });

        adapter.notifyDataSetChanged();
    }

    private ArrayList<Business> getRecommendations(List<Business> businesses, int num) {
        ArrayList<Business> recommendations = new ArrayList<>(num);
        for (int i = 0; i < num; ++i) {
            recommendations.add(businesses.get(i));
        }
        return recommendations;
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

    private void initToolbar() {
        final CollapsingToolbarLayout toolbar
                = (CollapsingToolbarLayout)findViewById(R.id.app_toolbar_rec);
        toolbar.setTitle(" ");
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.app_bar_rec);
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

}
