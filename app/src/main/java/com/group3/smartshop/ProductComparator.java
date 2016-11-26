package com.group3.smartshop;

/**
 * Created by ZY on 11/25/16.
 */

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
    public int compare(Product p1, Product p2)
    {
      if(p1.getHowMuch() > p2.getHowMuch())
      {
        return 1;
      }
      return -1;
    }
}
