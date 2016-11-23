package com.group3.smartshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by samxzy0207 on 11/21/16.
 */

public class Utility {

    public static List<Business> sortBusinessesRating(List<Business> busi) {
        List<Business> businesses1 = new ArrayList<Business>(busi);
        Collections.sort(businesses1, new BusinessComparator());
        return businesses1;
    }

}
