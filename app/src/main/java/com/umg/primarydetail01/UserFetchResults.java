package com.umg.primarydetail01;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserFetchResults {
    @SerializedName("results")
    @Expose
    private ArrayList results;

    public ArrayList getResults() {
        return results;
    }
}
