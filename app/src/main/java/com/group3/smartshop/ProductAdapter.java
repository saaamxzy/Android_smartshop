package com.group3.smartshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Isabella on 2016/11/11.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{

    private Context myContext;
    private List<Product> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, price;
        public ImageView cardImage, favButton;

        public MyViewHolder(View view){
            super(view);
            productName = (TextView) view.findViewById(R.id.card_title);
            price = (TextView) view.findViewById(R.id.card_price);
            cardImage = (ImageView) view.findViewById(R.id.card_image);
            favButton = (ImageView) view.findViewById(R.id.card_fav_button);
        }
    }

    public ProductAdapter (Context myContext, List<Product> productList){
        this.myContext = myContext;
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.card_product, parent, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder (final MyViewHolder holder, int index){
        Product item = productList.get(index);
        String name = item.getName();
        String tempLink = item.getLink();
        String origin = item.getOrigin();
        if(tempLink.charAt(0) != 'h' && origin.equalsIgnoreCase("Amazon"))
        {
          tempLink = "http://www.amazon.com"+tempLink;
          //System.out.println("This is tempLink: "+tempLink);

        }
        final String link = tempLink;

        holder.productName.setText(name);
        holder.productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext,WebViewActivity.class);
                intent.putExtra(OnlineSearchActivity.EXTRA_MESSAGE,link);
                myContext.startActivity(intent);
            }
        });


        holder.price.setText("$" + item.getHowMuch() + "\n" + "Sell by " + item.getOrigin());


        Glide.with(myContext).load(item.getImg()).into(holder.cardImage);
        holder.cardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext,WebViewActivity.class);
                intent.putExtra(OnlineSearchActivity.EXTRA_MESSAGE,link);
                myContext.startActivity(intent);
            }
        });

        holder.favButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showMenu(holder.favButton);
            }
        });
    }

    @Override
    public int getItemCount(){
        return productList.size();
    }

    private void showMenu(View view){
        PopupMenu menu = new PopupMenu(myContext, view);
        MenuInflater inf = menu.getMenuInflater();
        inf.inflate(R.menu.menu_product, menu.getMenu());
        menu.setOnMenuItemClickListener(new MenuClickListener());
        menu.show();
    }

    class MenuClickListener implements  PopupMenu.OnMenuItemClickListener{

        public MenuClickListener(){}

        @Override
        public boolean onMenuItemClick (MenuItem menuItem){
            switch (menuItem.getItemId()) {
                case R.id.add_favourite:
                    Toast.makeText(myContext, "Add to favorite", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
