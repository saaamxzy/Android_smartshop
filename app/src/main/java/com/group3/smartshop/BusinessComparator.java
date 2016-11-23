package com.group3.smartshop;

import java.util.Comparator;

/**
 * Created by samxzy0207 on 11/21/16.
 */

public class BusinessComparator implements Comparator<Business> {
    @Override
    public int compare(Business lhs, Business rhs) {
        return lhs.getRating().compareTo(rhs.getRating());
    }
}