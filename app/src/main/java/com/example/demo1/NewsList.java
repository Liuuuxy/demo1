package com.example.demo1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsList {
    @SerializedName("result")
    @Expose
    private List<News> result;

    public List<News> getNews() {
        return result;
    }

    public void setNews(List<News> result) {
        this.result = result;
    }
}
